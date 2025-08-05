package com.cdac.service;
import com.cdac.dao.BookingDao;
import com.cdac.dao.NotificationDao;
import com.cdac.dao.PackageDao;
import com.cdac.dao.UserDao;
import com.cdac.dao.VehicleDao;
import com.cdac.dao.WasherDao;
import com.cdac.dto.NotificationDto;
import com.cdac.entities.NotificationEntity;
import com.cdac.entities.UserEntity;
import com.cdac.security.JwtUtils;
import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.service.NotificationService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserDao userDao;

    @Override
    public NotificationDto createNotification(NotificationDto dto, String userEmail) {
        UserEntity user = userDao.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        NotificationEntity entity = new NotificationEntity();
        entity.setUser(user);
        entity.setMessage(dto.getMessage());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setCreatedAt(LocalDateTime.now());

        NotificationEntity saved = notificationDao.save(entity);
        return mapToDto(saved);
    }

    @Override
    public List<NotificationDto> getNotificationsByUserEmail(String email) {
        UserEntity user = userDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return notificationDao.findAll()
                .stream()
                .filter(n -> n.getUser().getId().equals(user.getId()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        return notificationDao.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        return notificationDao.findAll()
                .stream()
                .filter(n -> n.getUser().getId().equals(userId))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationDto dto) {
        NotificationEntity entity = notificationDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));

        entity.setMessage(dto.getMessage());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        NotificationEntity updated = notificationDao.save(entity);

        return mapToDto(updated);
    }

    @Override
    public void deleteNotification(Long id) {
        NotificationEntity entity = notificationDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        notificationDao.delete(entity);
    }
    
    @Override
    public NotificationDto getNotificationById(Long id) {
        NotificationEntity entity = notificationDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        return mapToDto(entity);
    }

    private NotificationDto mapToDto(NotificationEntity e) {
        NotificationDto dto = new NotificationDto();
        dto.setId(e.getId());
        dto.setUserId(e.getUser().getId());
        dto.setMessage(e.getMessage());
        dto.setType(e.getType());
        dto.setStatus(e.getStatus());
        dto.setCreatedAt(e.getCreatedAt().toString());
        return dto;
    }
}
