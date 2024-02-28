package com.java.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.dto.PositionDto;
import com.java.ams.model.Position;

@Service
public interface PositionService {

	int getPositionCount();

	boolean save(PositionDto positionDto);

	List<Position> getAllPositions();

}
