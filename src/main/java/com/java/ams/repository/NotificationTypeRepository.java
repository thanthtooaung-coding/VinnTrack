package com.java.ams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.ams.model.NotificationType;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Integer> {

}
