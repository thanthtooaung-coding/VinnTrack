package com.java.ams.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.model.Notification;
import com.java.ams.repository.NotificationRepository;
import com.java.ams.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplement implements NotificationService {

	private final NotificationRepository repo;
	
	@Override
	public int getNotificationCount() {
		return (int) repo.count();
	}

	@Override
	public List<Notification> getFiveNotifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notification> getLatestNotifications() {
		// TODO Auto-generated method stub
		return null;
	}

}
