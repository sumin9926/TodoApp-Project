package com.example.todoapp.service;

import com.example.todoapp.dto.ScheduleRequestDto;
import com.example.todoapp.dto.ScheduleResponseDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findSchedule(ZonedDateTime updatedDate, String name);
    ScheduleResponseDto updateDetails(Long scheduleId, String name, String password, String details);
    ScheduleResponseDto updateWholeSchedule(Long scheduleId, String name, String password, String details);
    ScheduleResponseDto updateAuthor(Long scheduleId, String name, String password, String details);
    void deleteSchedule(Long scheduleId);
}
