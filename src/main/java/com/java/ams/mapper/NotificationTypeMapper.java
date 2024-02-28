package com.java.ams.mapper;

import org.springframework.stereotype.Service;

import com.java.ams.dto.NotificationTypeDto;
import com.java.ams.model.NotificationType;

@Service
public class NotificationTypeMapper {

    public static NotificationTypeDto beanToDto(NotificationType notificationType) {
        NotificationTypeDto dto = new NotificationTypeDto();
        dto.setId(notificationType.getId());
        dto.setName(notificationType.getName());
        return dto;
    }

    public static NotificationType dtoToBean(NotificationTypeDto dto) {
        NotificationType notificationType = new NotificationType();
        notificationType.setId(dto.getId());
        notificationType.setName(dto.getName());
        return notificationType;
    }
}
