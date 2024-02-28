package com.java.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.model.LeaveRecord;

@Service
public interface LeaveRecordService {
	List<LeaveRecord> getAllLeaveRecords();
	void save(LeaveRecord leaveRecordBean);
	void deleteById(int id);
	void acceptById(int id);
	void rejectById(int id);
	LeaveRecord getLeaveRecordById(int id);
	int getLeaveDaysByUserId(int id);
}
