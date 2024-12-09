package com.example.todoapp.repository;

import com.example.todoapp.dto.ScheduleResponseDto;
import com.example.todoapp.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*DB에 일정 추가*/
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters=new HashMap<>();
        parameters.put("name",schedule.getName());
        parameters.put("details", schedule.getDetails());
        parameters.put("password",schedule.getPassword());
        parameters.put("created_date",schedule.getCreatedDate());
        parameters.put("updated_date",schedule.getUpdatedDate());

        Number key=jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getName(),schedule.getDetails(),
                                       schedule.getCreatedDate(),schedule.getUpdatedDate());
    }

    /*DB에서 id로 일정 찾기*/
    @Override
    public Schedule findScheduleByIdElseThrow(Long scheduleId) {
        List<Schedule> findScheduleById=jdbcTemplate.query("select * from schedule where schedule_id= ?", scheduleRowMapper() ,scheduleId);
        return findScheduleById.stream().findAny().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Dose not exists id="+scheduleId));
    }

    /*DB의 모든 일정 반환*/
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule", scheduleDtoRowMapper());
    }

    /*DB에서 조건에 맞는 일정 찾기*/
    @Override
    public List<ScheduleResponseDto> findSchedule(String updatedDate, String name) {
        // 이름으로 조회
        if(name!=null&&updatedDate==null) return jdbcTemplate.query("select * from schedule where name=?",scheduleDtoRowMapper(),name);
        // 수정일로 조회
        String formattedDate=updatedDate+"%";
        if(updatedDate!=null&&name==null) return jdbcTemplate.query("select * from schedule where updated_date like ?",scheduleDtoRowMapper(),formattedDate);
        //이름, 수정일로 조회
        return jdbcTemplate.query("select * from schedule where name=? AND updated_date like ?", scheduleDtoRowMapper(), name, formattedDate);
    }

    /*작성자, 일정 수정*/
    @Override
    public int updateWholeSchedule(Long scheduleId, String name, String password, String details) {
        int updateRowNum=0;

        //수정시간 갱신
        Timestamp updatedDate=Timestamp.from(ZonedDateTime.now().toInstant());

        if(isTruePassword(scheduleId,password)) {
            updateRowNum=jdbcTemplate.update("update schedule set name=?, details=?, updated_date=? where schedule_id=?", name, details, updatedDate, scheduleId);
        }
        return updateRowNum;
    }

    /*일정만 수정*/
    @Override
    public int updateDetails(Long scheduleId, String password, String details) {
        int updateRowNum=0;

        //수정시간 갱신
        Timestamp updatedDate=Timestamp.from(ZonedDateTime.now().toInstant());

        if(isTruePassword(scheduleId,password)) {
            updateRowNum=jdbcTemplate.update("update schedule set details=?, updated_date=? where schedule_id=?", details, updatedDate, scheduleId);
        }
        return updateRowNum;
    }

    /*작성자만 수정*/
    @Override
    public int updateAuthor(Long scheduleId, String name, String password) {
        int updateRowNum=0;

        //수정시간 갱신
        Timestamp updatedDate=Timestamp.from(ZonedDateTime.now().toInstant());

        if(isTruePassword(scheduleId,password)) {
            updateRowNum=jdbcTemplate.update("update schedule set name=?, updated_date=? where schedule_id=?", name, updatedDate, scheduleId);
        }
        return updateRowNum;
    }

    /*DB에서 일정 삭제*/
    @Override
    public int deleteSchedule(Long scheduleId) {
        return jdbcTemplate.update("delete from schedule where schedule_id=?", scheduleId);
    }

    /*비밀번호 대조*/
    private boolean isTruePassword(Long id, String password){
        try{
            //비밀번호 동일
            if(password.equals(jdbcTemplate.queryForObject("select password from schedule where schedule_id=?", String.class,id))) return true;
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password.");//비밀번호 다름
        } catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule dose not exist. id = "+id);
        } catch (IncorrectResultSizeDataAccessException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database query returned an unexpected number of results.");
        }
    }

    private RowMapper<ScheduleResponseDto> scheduleDtoRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("schedule_id"),
                        rs.getString("name"),
                        rs.getString("details"),
                        toZonedDateTime(rs.getTimestamp("created_date")),
                        toZonedDateTime(rs.getTimestamp("updated_date"))
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapper(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("schedule_id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("details"),
                        toZonedDateTime(rs.getTimestamp("created_date")),
                        toZonedDateTime(rs.getTimestamp("updated_date"))
                );
            }
        };
    }

    /*TimeStamp를 ZonedDateTime으로 변환*/
    private ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant().atZone(ZoneId.systemDefault()):null;
    }
}
