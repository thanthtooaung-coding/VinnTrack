package com.java.ams.mapper;

import com.java.ams.dto.UserDto;
import com.java.ams.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserMapper {

    public UserDto beanToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setDob(user.getDob().toLocalDate());
        dto.setGender(user.getGender());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setJoinDate(user.getJoinDate());
        dto.setPosition(user.getPosition());
        dto.setDepartment(user.getDepartment());
        dto.setNotifications(user.getNotifications());
        return dto;
    }

    public User dtoToBean(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        if (dto.getDob() != null) {
            user.setDob(java.sql.Date.valueOf(dto.getDob()));
        }
        user.setGender(dto.getGender());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setJoinDate(dto.getJoinDate());
        user.setPosition(dto.getPosition());
        user.setDepartment(dto.getDepartment());
        user.setNotifications(dto.getNotifications());
        return user;
    }
}
