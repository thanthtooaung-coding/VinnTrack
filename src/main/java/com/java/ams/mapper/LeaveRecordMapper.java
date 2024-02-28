package com.java.ams.mapper;

import org.springframework.stereotype.Service;

import com.java.ams.dto.LeaveRecordDto;
import com.java.ams.model.LeaveRecord;

@Service
public class LeaveRecordMapper {

    public LeaveRecordDto beanToDto(LeaveRecord leaveRecord) {
        LeaveRecordDto dto = new LeaveRecordDto();
        dto.setId(leaveRecord.getId());
        dto.setStatus(leaveRecord.getStatus());
        dto.setReason(leaveRecord.getReason());
        dto.setStartDate(leaveRecord.getStartDate());
        dto.setEndDate(leaveRecord.getEndDate());
        dto.setAccepted(leaveRecord.isAccepted());
        dto.setLeaveType(leaveRecord.getLeaveType());
        dto.setUser(leaveRecord.getUser());
        return dto;
    }

    public LeaveRecord dtoToBean(LeaveRecordDto dto) {
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setId(dto.getId());
        leaveRecord.setStatus(dto.getStatus());
        leaveRecord.setReason(dto.getReason());
        leaveRecord.setStartDate(dto.getStartDate());
        leaveRecord.setEndDate(dto.getEndDate());
        leaveRecord.setAccepted(dto.isAccepted());
        leaveRecord.setLeaveType(dto.getLeaveType());
        leaveRecord.setUser(dto.getUser());
        return leaveRecord;
    }
}
