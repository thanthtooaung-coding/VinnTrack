package com.java.ams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.ams.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	Department findById(int id);
}
