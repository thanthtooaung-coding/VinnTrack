package com.java.ams.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.model.LeaveRecord;
import com.java.ams.repository.LeaveRecordRepository;

import com.java.ams.service.LeaveRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveRecordServiceImplement implements LeaveRecordService {

	private final LeaveRecordRepository repo;

	@Override
	public List<LeaveRecord> getAllLeaveRecords() {
		return repo.findAll();
	}

	@Override
	public void save(LeaveRecord leaveRecordBean) {
		repo.save(leaveRecordBean);
	}

	@Override
	public void deleteById(int id) {
		repo.deleteById(id);
	}

	@Override
	public void acceptById(int id) {
		LeaveRecord leaveRecord = getLeaveRecordById(id);
		leaveRecord.setStatus("Accept");
		leaveRecord.setAccepted(true);
		repo.save(leaveRecord);
	}

	@Override
	public void rejectById(int id) {
		LeaveRecord leaveRecord = getLeaveRecordById(id);
		leaveRecord.setStatus("Reject");
		leaveRecord.setAccepted(false);
		repo.save(leaveRecord);
	}

	@Override
	public LeaveRecord getLeaveRecordById(int id) {
		return repo.findById(id);
	}

	@Override
	public int getLeaveDaysByUserId(int id) {
		List<LeaveRecord> leaveRecords = repo.findByUserId(id);
		int totalLeaveDays = 0;
		for (LeaveRecord leaveRecord : leaveRecords) {
			if (leaveRecord.isAccepted()) {
				totalLeaveDays += repo.getLeaveDays(id);
			}
		}
		return totalLeaveDays;
	}
}
