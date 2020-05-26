package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Notification;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long>{

}
