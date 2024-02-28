package com.java.ams.model;

import java.sql.*;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "attendance")
@Getter
@Setter
@RequiredArgsConstructor
public class AttendanceRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String attendanceStatus;
    
    @Column
    private Timestamp timeIn;
    
    @Column
    private Timestamp timeOut;
    
    private boolean isClicked;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    private LocalDate date;
    
    @Column(name = "first_click")
    private Boolean firstClick;
}
