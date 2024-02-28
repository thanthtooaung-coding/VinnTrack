package com.java.ams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.ams.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
