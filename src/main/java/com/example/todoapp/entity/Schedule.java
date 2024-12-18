package com.example.todoapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class Schedule {
    private Long scheduleId;
    private String name;
    private String password;
    private String details;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime updatedDate;

    //새로운 일정 생성시 사용
    public Schedule(String name, String details, String password){
        this.name=name;
        this.details=details;
        this.password=password;
        this.createdDate=ZonedDateTime.now();
        this.updatedDate=this.createdDate; //최초 입력 시, 수정일은 작성일과 동일
    }
}
