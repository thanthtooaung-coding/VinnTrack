package com.java.ams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.ams.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
	boolean existsByEmail(@Param("email") String email);
	
	User findUserByEmail(String email);
	User findById(int id);

	@Query("SELECT u FROM User u JOIN FETCH u.position")
	List<User> findAllWithPosition();
	List<User> findAllByRole(String role);
	
	int countByRole(String role);
	
	@Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT a.user.id FROM AttendanceRecord a WHERE a.date = CURRENT_DATE AND a.isClicked = true) AND u.role = 'EMPLOYEE'")
	List<User> findAllUsersWhoHaveNotClickedToday();
	
	@Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT a.user.id FROM AttendanceRecord a WHERE a.date = CURRENT_DATE AND a.attendanceStatus = 'Present')")
	List<User> findAllAbsentUsersToday();
}
