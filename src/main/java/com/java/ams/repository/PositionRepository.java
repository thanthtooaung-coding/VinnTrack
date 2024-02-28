package com.java.ams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java.ams.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
	@Query("SELECT p FROM Position p ORDER BY p.salary DESC, p.name ASC")
	List<Position> findAllOrderedByNameAscAndSalaryDesc();
}
