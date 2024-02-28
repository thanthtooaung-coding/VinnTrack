package com.java.ams.mapper;

import org.springframework.stereotype.Service;

import com.java.ams.dto.NotificationDto;
import com.java.ams.model.Notification;

@Service
public class NotificationMapper {

    public static NotificationDto beanToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setUser(notification.getUser());
        dto.setNotificationType(notification.getNotificationType());
        dto.setNotificationText(notification.getNotificationText());
        dto.setStatus(notification.isStatus());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }

    public static Notification dtoToBean(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setUser(dto.getUser());
        notification.setNotificationType(dto.getNotificationType());
        notification.setNotificationText(dto.getNotificationText());
        notification.setStatus(dto.isStatus());
        notification.setCreatedAt(dto.getCreatedAt());
        return notification;
    }
}
