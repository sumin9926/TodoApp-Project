package com.example.todoapp.repository;

import com.example.todoapp.dto.ScheduleResponseDto;
import com.example.todoapp.entity.Schedule;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule")
                .usingColumns("name", "details","password","created_date","updated_date")
                .usingGeneratedKeyColumns("schedule_id");

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

    @Override
    public Schedule findScheduleByIdElseThrow(Long scheduleId) {
        List<Schedule> findScheduleById=jdbcTemplate.query("select * from schedule where schedule_id= ?", scheduleRowMapper() ,scheduleId);
        return findScheduleById.stream().findAny().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Dose not exists id="+scheduleId));
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return List.of();
    }

    @Override
    public List<ScheduleResponseDto> findSchedule(ZonedDateTime updatedDate, String name) {
        return List.of();
    }

    @Override
    public int updateDetails(Long scheduleId, String name, String password, String details) {
        return 0;
    }

    @Override
    public int updateWholeSchedule(Long scheduleId, String name, String password, String details) {
        return 0;
    }

    @Override
    public int updateAuthor(Long scheduleId, String name, String password, String details) {
        return 0;
    }

    @Override
    public int deleteSchedule(Long scheduleId) {
        return 0;
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

    private ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant().atZone(ZoneId.systemDefault()):null;
    }
}
