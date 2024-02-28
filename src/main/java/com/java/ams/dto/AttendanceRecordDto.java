package com.java.ams.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.java.ams.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AttendanceRecordDto {
private int id;
    
    private String attendanceStatus;
    
    private Timestamp timeIn;
    private Timestamp timeInTest;
    
    private Timestamp timeOut;
    
    private boolean isClicked;
    
    private User user;
    
    private LocalDate date;
}
