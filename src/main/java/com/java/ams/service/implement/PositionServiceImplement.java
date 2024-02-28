package com.java.ams.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.dto.PositionDto;
import com.java.ams.mapper.PositionMapper;
import com.java.ams.model.Position;
import com.java.ams.repository.PositionRepository;
import com.java.ams.service.PositionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionServiceImplement implements PositionService {
	
	private final PositionRepository repo;
	
	@Override
	public int getPositionCount() {
		return (int) repo.count();
	}

	@Override
	public boolean save(PositionDto positionDto) {
		Position positionBean = PositionMapper.dtoToBean(positionDto);
		repo.save(positionBean);
		return true;
		
	}

	@Override
	public List<Position> getAllPositions() {
		return repo.findAllOrderedByNameAscAndSalaryDesc();
	}

}
