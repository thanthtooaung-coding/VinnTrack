package com.java.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.dto.DepartmentDto;
import com.java.ams.model.Department;

@Service
public interface DepartmentService {
	boolean save (DepartmentDto departmentDto);
	boolean update (int id, DepartmentDto departmentDto);
	int getDepartmentCount();
	List<Department> getAllDepartments();
	void deleteDepartment(int id);
}
