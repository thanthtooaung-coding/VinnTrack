package com.java.ams.mapper;

import org.springframework.stereotype.Service;

import com.java.ams.dto.DepartmentDto;
import com.java.ams.model.Department;

@Service
public class DepartmentMapper {

    public static DepartmentDto beanToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setUsers(department.getUsers());
        return dto;
    }

    public static Department dtoToBean(DepartmentDto dto) {
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setUsers(dto.getUsers());
        return department;
    }
}
