package com.example.todoapp.controller;

import com.example.todoapp.dto.ScheduleRequestDto;
import com.example.todoapp.dto.ScheduleResponseDto;
import com.example.todoapp.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long scheduleId){
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId), HttpStatus.OK);
    }

    /*조건에 맞는 일정 조회*/
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findSchedule(@RequestParam(required = false) LocalDate updatedDate, @RequestParam(required = false) String name){
        return new ResponseEntity<>(scheduleService.findSchedule(updatedDate, name),HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateAuthor(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateAuthor(scheduleId, dto.getName(), dto.getPassword(), dto.getDetails()),HttpStatus.OK);
    }
}
