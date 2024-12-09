package com.example.todoapp.service;

import com.example.todoapp.dto.ScheduleRequestDto;
import com.example.todoapp.dto.ScheduleResponseDto;
import com.example.todoapp.entity.Schedule;
import com.example.todoapp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ScheduleServieImpl implements ScheduleService{

    private final JdbcTemplate jdbcTemplate;
    private final ScheduleRepository scheduleRepository;

    public ScheduleServieImpl(ScheduleRepository scheduleRepository, JdbcTemplate jdbcTemplate) {
        this.scheduleRepository = scheduleRepository;
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule=new Schedule(dto.getName(),dto.getDetails(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        Schedule schedule=scheduleRepository.findScheduleByIdElseThrow(scheduleId);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findSchedule(LocalDate updatedDate, String name) {
        if(updatedDate==null && name==null) return scheduleRepository.findAllSchedules(); //전체 일정 조회
        String formattedDate = updatedDate != null ? updatedDate.toString() : null;
        return scheduleRepository.findSchedule(formattedDate, name); //특정 조건에 맞는 일정 조회
    }

    @Override
    public ScheduleResponseDto updateWholeSchedule(Long scheduleId, String name, String password, String details) {
        return null;
    }

    @Override
    public ScheduleResponseDto updateDetails(Long scheduleId, String name, String password, String details) {
        if(name!=null||details==null||password==null){ //필수 정보 누락 혹은 불필요한 정보를 포함한 경우
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only details and password are required.");
        }

        int updatedRowNum=scheduleRepository.updateDetails(scheduleId,password,details);
        if(updatedRowNum==0){//변경하려는 일정이 존재하지 않을 경우
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule dose not exist. id = "+scheduleId);
        }

        Schedule schedule=scheduleRepository.findScheduleByIdElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateAuthor(Long scheduleId, String name, String password, String details) {
        if(name==null||details!=null||password==null){ //필수 정보 누락 혹은 불필요한 정보를 포함한 경우
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only the author name and password are required.");
        }

        int updatedRowNum=scheduleRepository.updateAuthor(scheduleId,name,password);
        if(updatedRowNum==0){//변경하려는 일정이 존재하지 않을 경우
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule dose not exist. id = "+scheduleId);
        }

        Schedule schedule=scheduleRepository.findScheduleByIdElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {

    }
}
