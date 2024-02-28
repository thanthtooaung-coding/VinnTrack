package com.java.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.dto.UserDto;
import com.java.ams.model.User;

@Service
public interface UserService {
	boolean save (UserDto userDto);
	boolean update (int id,UserDto userDto);
	String hashPassword(String plainTextPassword);
	boolean checkPassword(String inputPassword, String hashedPassword);
	User findUser(String email);
	boolean duplicateUser(String email);
	boolean changePassword(int id, UserDto userDto);
	User getUserById(int id);
	int getUserCount();
	int getCountByRole(String role);
	List<User> getAllUsers();	
	void delete(int id);
	List<User> getAllByRole(String role);
	User updateProfileImage(UserDto userDto, int id);
	User updateProfileName(UserDto userDto, int id);
	void sendEmailToAllUsers();
	double calculateSalary(int id);
}
