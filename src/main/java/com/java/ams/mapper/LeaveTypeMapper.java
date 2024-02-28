package com.java.ams.mapper;

import org.springframework.stereotype.Service;

import com.java.ams.dto.LeaveTypeDto;
import com.java.ams.model.LeaveType;

@Service
public class LeaveTypeMapper {

    public LeaveTypeDto beanToDto(LeaveType leaveType) {
        LeaveTypeDto dto = new LeaveTypeDto();
        dto.setId(leaveType.getId());
        dto.setName(leaveType.getName());
        return dto;
    }

    public LeaveType dtoToBean(LeaveTypeDto dto) {
        LeaveType leaveType = new LeaveType();
        leaveType.setId(dto.getId());
        leaveType.setName(dto.getName());
        return leaveType;
    }
}
