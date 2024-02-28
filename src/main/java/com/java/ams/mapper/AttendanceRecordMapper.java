package com.java.ams.mapper;

import org.springframework.stereotype.Service;

import com.java.ams.dto.AttendanceRecordDto;
import com.java.ams.model.AttendanceRecord;

@Service
public class AttendanceRecordMapper {

    public AttendanceRecordDto beanToDto(AttendanceRecord attendanceRecord) {
        AttendanceRecordDto dto = new AttendanceRecordDto();
        dto.setId(attendanceRecord.getId());
        dto.setAttendanceStatus(attendanceRecord.getAttendanceStatus());
        dto.setTimeIn(attendanceRecord.getTimeIn());
        dto.setTimeOut(attendanceRecord.getTimeOut());
        dto.setUser(attendanceRecord.getUser());
        dto.setClicked(attendanceRecord.isClicked());
        dto.setDate(attendanceRecord.getDate());
        return dto;
    }

    public AttendanceRecord dtoToBean(AttendanceRecordDto dto) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setId(dto.getId());
        attendanceRecord.setAttendanceStatus(dto.getAttendanceStatus());
        attendanceRecord.setTimeIn(dto.getTimeIn());
        attendanceRecord.setTimeOut(dto.getTimeOut());
        attendanceRecord.setUser(dto.getUser());
        attendanceRecord.setClicked(dto.isClicked());
        attendanceRecord.setDate(dto.getDate());
        return attendanceRecord;
    }
}
