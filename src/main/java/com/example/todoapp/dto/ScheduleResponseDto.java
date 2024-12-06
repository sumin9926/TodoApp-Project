package com.example.todoapp.dto;

import com.example.todoapp.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long scheduleId;
    private String name;
    private String details;
    @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private ZonedDateTime createdDate;

    @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private ZonedDateTime updatedDate;

    public ScheduleResponseDto(Schedule schedule){
        this.scheduleId=schedule.getScheduleId();
        this.details=schedule.getDetails();
        this.name=schedule.getName();
        this.createdDate=schedule.getCreatedDate();
        this.updatedDate=schedule.getUpdatedDate();
    }
}
