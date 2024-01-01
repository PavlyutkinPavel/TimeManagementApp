package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Notification;
import com.timemanagement.timemanagementrest.domain.dto.NotificationDTO;
import com.timemanagement.timemanagementrest.repository.NotificationRepository;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final Notification notification;
    private final SecurityService securityService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, Notification notification, SecurityService securityService) {
        this.notificationRepository = notificationRepository;
        this.notification = notification;
        this.securityService = securityService;
    }

    public List<Notification> getNotifications() {
        return notificationRepository.findAllNotifications();
    }

    public Optional<Notification> getNotification(Long id) {
        return notificationRepository.findByIdNotification(id);
    }

    public void createNotification(NotificationDTO notificationDTO, Principal principal) {
        notification.setId(notificationRepository.getNextSequenceValue());
        notification.setCreated_at(LocalDateTime.now());
        notification.setMessage(notificationDTO.getMessage());
        notification.setUserId(securityService.getUserIdByLogin(principal.getName()));
        notificationRepository.saveNotification(notification);
    }

    public void updateNotification(Notification notification) {
        notificationRepository.saveAndFlushNotification(notification);
    }

    public void deleteNotificationById(Long id) {
        notificationRepository.deleteByIdNotification(id);
    }

}