package com.java.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.model.LeaveType;

@Service
public interface LeaveTypeService {

	List<LeaveType> getAllLeaveTypes();
	LeaveType getLeaveTypeById(int id);
}
