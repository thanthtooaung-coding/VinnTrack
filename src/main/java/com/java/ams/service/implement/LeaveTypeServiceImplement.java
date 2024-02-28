package com.java.ams.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.model.LeaveType;
import com.java.ams.repository.LeaveTypeRepository;
import com.java.ams.service.LeaveTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveTypeServiceImplement implements LeaveTypeService {
	
	private final LeaveTypeRepository repo;
	
	@Override
	public List<LeaveType> getAllLeaveTypes() {
		return repo.findAll();
	}

	@Override
	public LeaveType getLeaveTypeById(int id) {
		return repo.findById(id);
	}

}
