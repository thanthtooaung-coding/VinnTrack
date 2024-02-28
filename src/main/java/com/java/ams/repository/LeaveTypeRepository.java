package com.java.ams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.ams.model.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
	LeaveType findById(int id);
}
