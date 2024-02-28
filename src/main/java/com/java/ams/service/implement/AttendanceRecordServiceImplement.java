package com.java.ams.service.implement;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.dto.AttendanceRecordDto;
import com.java.ams.mapper.AttendanceRecordMapper;
import com.java.ams.model.AttendanceRecord;
import com.java.ams.model.User;
import com.java.ams.repository.AttendanceRecordRepository;
import com.java.ams.repository.UserRepository;
import com.java.ams.service.AttendanceRecordService;
import com.java.ams.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceRecordServiceImplement implements AttendanceRecordService {

	private final AttendanceRecordRepository repo;
	private final UserRepository userRepo;
	private final AttendanceRecordMapper attendanceRecordMapper;
	private final UserService userService;

	@Override
	public List<AttendanceRecord> getAllAttendanceRecord() {
		return repo.findAll();
	}

	@Override
	public boolean takeAttendance(AttendanceRecordDto attendanceRecordDto, int id) {
		AttendanceRecord attendanceRecord = attendanceRecordMapper.dtoToBean(attendanceRecordDto);
		User findUser = userService.getUserById(id);
		attendanceRecord.setAttendanceStatus("Present");
		attendanceRecord.setUser(findUser);
		attendanceRecord.setDate(LocalDate.now());
		AttendanceRecord existingAttendanceRecord = repo.findByUserAndDate(findUser, LocalDate.now());

		if (existingAttendanceRecord != null) {
			if (existingAttendanceRecord.getTimeIn() == null) {
				existingAttendanceRecord.setTimeIn(new Timestamp(System.currentTimeMillis()));
			} else {
				existingAttendanceRecord.setTimeOut(new Timestamp(System.currentTimeMillis()));
			}
			repo.save(existingAttendanceRecord);
			return false;
		} else {
			attendanceRecord.setTimeIn(new Timestamp(System.currentTimeMillis()));
			attendanceRecord.setClicked(true);
			attendanceRecord.setFirstClick(true);
			repo.save(attendanceRecord);
			return true;
		}
	}

	@Override
	public boolean isFirstClickToday(int userId) {
		AttendanceRecord attendanceRecord = repo.findTodayAttendanceRecord(userService.getUserById(userId));
		return attendanceRecord != null && Boolean.TRUE.equals(attendanceRecord.getFirstClick());
	}

	@Override
	public void storeFirstClick(int userId) {
		AttendanceRecord attendanceRecord = repo.findTodayAttendanceRecord(userService.getUserById(userId));
		if (attendanceRecord != null) {
			attendanceRecord.setFirstClick(true);
			repo.save(attendanceRecord);
		}
	}

	@Override
	public void setAbsentIfNotClicked() {
	    List<User> usersWhoHaveNotClickedToday = userRepo.findAllUsersWhoHaveNotClickedToday();
	    for (User user : usersWhoHaveNotClickedToday) {
	        AttendanceRecord attendanceRecord = new AttendanceRecord();
	        attendanceRecord.setUser(user);
	        attendanceRecord.setDate(LocalDate.now());
	        attendanceRecord.setAttendanceStatus("Absent");
	        attendanceRecord.setClicked(true);
	        attendanceRecord.setFirstClick(false);
	        repo.save(attendanceRecord);
	    }
	}

	@Override
	public List<AttendanceRecord> displayDataForToday() {
		List<AttendanceRecord> records = repo.findRecordsForToday();
		return records;
	}

	@Override
	public List<AttendanceRecord> getRecordsNotForToday() {
		List<AttendanceRecord> records = repo.findRecordsNotForToday();
		return records;
	}

	@Override
	public int getcountTodayAttendanceRecords() {
		return repo.countTodayAttendanceRecords();
	}

	@Override
	public List<AttendanceRecord> getRecordsNotPresentForToday() {
		List<AttendanceRecord> records = repo.findRecordsNotPresentForToday();
		return records;
	}

}
