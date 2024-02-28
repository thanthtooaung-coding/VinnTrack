package com.java.ams.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.java.ams.model.AttendanceRecord;
import com.java.ams.service.AttendanceRecordService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataDisplayScheduler {

	private final AttendanceRecordService attendanceService;
	private List<AttendanceRecord> recordsForToday;

	@Scheduled(cron = "0 0 20 * * *")
	public void displayData() {
		recordsForToday = attendanceService.displayDataForToday();
	}

	public List<AttendanceRecord> getRecordsForToday() {
		return recordsForToday;
	}
}
