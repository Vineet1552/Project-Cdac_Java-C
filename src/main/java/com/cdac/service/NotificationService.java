package com.cdac.service;

import java.util.List;

import com.cdac.dto.NotificationDto;

public interface NotificationService {
    NotificationDto createNotification(NotificationDto notificationDto);
    NotificationDto getNotificationById(Long id);
    List<NotificationDto> getAllNotifications();
    List<NotificationDto> getNotificationsByUserId(Long userId);
    NotificationDto updateNotification(Long id, NotificationDto notificationDto);
    void deleteNotification(Long id);
}
