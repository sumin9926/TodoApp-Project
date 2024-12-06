package com.example.todoapp.repository;

import com.example.todoapp.dto.ScheduleResponseDto;
import com.example.todoapp.entity.Schedule;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findSchedule(ZonedDateTime updatedDate, String name);
    int updateDetails(Long scheduleId, String name, String password, String details);
    int updateWholeSchedule(Long scheduleId, String name, String password, String details);
    int updateAuthor(Long scheduleId, String name, String password, String details);
    int deleteSchedule(Long scheduleId);

}
