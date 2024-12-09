package com.example.todoapp.service;

import com.example.todoapp.dto.ScheduleRequestDto;
import com.example.todoapp.dto.ScheduleResponseDto;
import com.example.todoapp.entity.Schedule;
import com.example.todoapp.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
    public List<ScheduleResponseDto> findAllSchedules() {
        return List.of();
    }

    @Override
    public List<ScheduleResponseDto> findSchedule(ZonedDateTime updatedDate, String name) {
        return null;
    }

    @Override
    public ScheduleResponseDto updateDetails(Long scheduleId, String name, String password, String details) {
        return null;
    }

    @Override
    public ScheduleResponseDto updateWholeSchedule(Long scheduleId, String name, String password, String details) {
        return null;
    }

    @Override
    public ScheduleResponseDto updateAuthor(Long scheduleId, String name, String password, String details) {
        return null;
    }

    @Override
    public void deleteSchedule(Long scheduleId) {

    }
}
