package com.java.ams.repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.ams.model.AttendanceRecord;
import com.java.ams.model.User;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {	
	
	@Query("SELECT a FROM AttendanceRecord a WHERE a.user = :user AND DATE(a.timeIn) = CURRENT_DATE")
	AttendanceRecord findTodayAttendanceRecord(@Param("user") User user);

	@Modifying
	@Query("UPDATE AttendanceRecord a SET a.timeOut = :timeOut WHERE a.id = :id")
	void updateTimeOut(@Param("id") int id, @Param("timeOut") Time timeOut);

	AttendanceRecord findByUserAndDate(User findUser, LocalDate timeIn);
	
	@Query("SELECT a FROM AttendanceRecord a WHERE DATE(a.timeIn) = CURRENT_DATE")
	List<AttendanceRecord> findRecordsForToday();
	
	@Query("SELECT a FROM AttendanceRecord a WHERE DATE(a.timeIn) != CURRENT_DATE")
	List<AttendanceRecord> findRecordsNotForToday();

	@Query("SELECT COUNT(a) FROM AttendanceRecord a WHERE DATE(a.timeIn) = CURRENT_DATE")
	int countTodayAttendanceRecords();
	
	@Query("SELECT a FROM AttendanceRecord a WHERE DATE(a.date) < CURRENT_DATE OR (DATE(a.date) = CURRENT_DATE AND a.attendanceStatus != 'Present')")
	List<AttendanceRecord> findRecordsNotPresentForToday();
}
