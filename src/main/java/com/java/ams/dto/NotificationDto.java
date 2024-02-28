package com.java.ams.dto;

import java.time.LocalDate;

import com.java.ams.model.NotificationType;
import com.java.ams.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NotificationDto {
	private int id;
	
	private User user;
	private NotificationType notificationType;
	
	private String notificationText;
    private boolean status;
    private LocalDate createdAt;
}
