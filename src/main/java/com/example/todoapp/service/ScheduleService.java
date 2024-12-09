package com.example.todoapp.service;

import com.example.todoapp.dto.ScheduleRequestDto;
import com.example.todoapp.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    List<ScheduleResponseDto> findSchedule(LocalDate updatedDate, String name);
    ScheduleResponseDto updateDetails(Long scheduleId, String name, String password, String details);
    ScheduleResponseDto updateWholeSchedule(Long scheduleId, String name, String password, String details);
    ScheduleResponseDto updateAuthor(Long scheduleId, String name, String password, String details);
    void deleteSchedule(Long scheduleId, String password);
}
