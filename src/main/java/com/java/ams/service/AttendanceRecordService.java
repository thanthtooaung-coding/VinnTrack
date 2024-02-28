package com.java.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.dto.AttendanceRecordDto;
import com.java.ams.model.AttendanceRecord;

@Service
public interface AttendanceRecordService {

	List<AttendanceRecord> getAllAttendanceRecord();
	boolean takeAttendance(AttendanceRecordDto attendanceRecordDto, int id);
	boolean isFirstClickToday(int userId);
	void storeFirstClick(int userId);
	List<AttendanceRecord> displayDataForToday();
	List<AttendanceRecord> getRecordsNotForToday();
	int getcountTodayAttendanceRecords();
	void setAbsentIfNotClicked();
	List<AttendanceRecord> getRecordsNotPresentForToday();
}
