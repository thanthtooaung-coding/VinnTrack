package com.java.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.ams.model.Notification;

@Service
public interface NotificationService {

	int getNotificationCount();

	List<Notification> getFiveNotifications();

	List<Notification> getLatestNotifications();

}
