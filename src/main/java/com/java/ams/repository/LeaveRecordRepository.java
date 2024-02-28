package com.java.ams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java.ams.model.LeaveRecord;

public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Integer> {
	LeaveRecord findById(int id);
	List<LeaveRecord> findByUserId(int userId);
	
	@Query("SELECT COUNT(*) FROM LeaveRecord lr WHERE lr.user.id = :userId AND lr.isAccepted = true")
	int getLeaveDays(int userId);
}
