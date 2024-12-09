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

    /*일정 추가*/
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    /*id로 일정 조회*/
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long scheduleId){
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId), HttpStatus.OK);
    }

    /*조건에 맞는 일정 조회*/
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findSchedule(@RequestParam(required = false) LocalDate updatedDate, @RequestParam(required = false) String name){
        return new ResponseEntity<>(scheduleService.findSchedule(updatedDate, name),HttpStatus.OK);
    }

    /*작성자 수정*/
    @PatchMapping("/{scheduleId}/author")
    public ResponseEntity<ScheduleResponseDto> updateAuthor(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateAuthor(scheduleId, dto.getName(), dto.getPassword(), dto.getDetails()),HttpStatus.OK);
    }

    /*일정 내용 수정*/
    @PatchMapping("/{scheduleId}/details")
    public ResponseEntity<ScheduleResponseDto> updateDetails(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateDetails(scheduleId, dto.getName(), dto.getPassword(), dto.getDetails()),HttpStatus.OK);
    }

    /*작성자, 일정 수정*/
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateWholeSchedule(scheduleId, dto.getName(), dto.getPassword(), dto.getDetails()),HttpStatus.OK);
    }

    /*일정 삭제*/
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
