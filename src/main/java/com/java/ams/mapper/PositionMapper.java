package com.java.ams.mapper;

import org.springframework.stereotype.Service;

import com.java.ams.dto.PositionDto;
import com.java.ams.model.Position;

@Service
public class PositionMapper {

    public static PositionDto beanToDto(Position position) {
        PositionDto dto = new PositionDto();
        dto.setId(position.getId());
        dto.setName(position.getName());
        dto.setSalary(position.getSalary());
        dto.setUsers(position.getUsers());
        return dto;
    }

    public static Position dtoToBean(PositionDto dto) {
        Position position = new Position();
        position.setId(dto.getId());
        position.setName(dto.getName());
        position.setSalary(dto.getSalary());
        position.setUsers(dto.getUsers());
        return position;
    }
}
