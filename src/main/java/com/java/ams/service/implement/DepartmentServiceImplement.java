package com.java.ams.service.implement;

import java.util.List;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.java.ams.dto.DepartmentDto;
import com.java.ams.mapper.DepartmentMapper;
import com.java.ams.model.Department;
import com.java.ams.repository.DepartmentRepository;
import com.java.ams.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImplement implements DepartmentService {

	private final DepartmentRepository repo;

	@Override
	public int getDepartmentCount() {
		return (int) repo.count();
	}

	@Override
	public boolean save(DepartmentDto departmentDto) {
		Department departmentBean = DepartmentMapper.dtoToBean(departmentDto);
		repo.save(departmentBean);
		return true;
	}

	@Override
	public List<Department> getAllDepartments() {
		List<Department> departments = repo.findAll();
		departments.sort(Comparator.comparing(Department::getName));
		return departments;
	}

	@Override
	public boolean update(int id, DepartmentDto departmentDto) {
		Department departmentBean = this.getDepartmentById(id);
		if (departmentBean == null) {
			throw new IllegalArgumentException("User not found with id: " + id);
		}
		departmentBean.setName(departmentDto.getName());
		repo.save(departmentBean);
		return false;
	}

	private Department getDepartmentById(int id) {
		return repo.findById(id);
	}

	@Override
	public void deleteDepartment(int id) {
		repo.deleteById(id);
	}

}
