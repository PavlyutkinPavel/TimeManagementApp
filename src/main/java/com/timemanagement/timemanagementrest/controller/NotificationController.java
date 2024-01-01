package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Notification;
import com.timemanagement.timemanagementrest.domain.Review;
import com.timemanagement.timemanagementrest.domain.dto.NotificationDTO;
import com.timemanagement.timemanagementrest.exception.NotificationNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Notification Controller", description = "makes all operations with notifications")
@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final SecurityService securityService;

    public NotificationController(NotificationService notificationService, SecurityService securityService) {
        this.notificationService = notificationService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all notifications(for all authorized users)")
    @GetMapping
    public ResponseEntity<List<Notification>> getMyNotifications(@RequestParam String myLogin,  Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || myLogin.equals(principal.getName().toString())){
            List<Notification> notifications = notificationService.getNotifications();
            if (notifications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(notifications, HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get specific notification(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        Notification notification = notificationService.getNotification(id).orElseThrow(NotificationNotFoundException::new);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @Operation(summary = "create notification(for all authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createNotification(@Valid @RequestBody NotificationDTO notificationDTO, Principal principal) {
        notificationService.createNotification(notificationDTO, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update notifications(for all admins and notification's author)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateNotification(@Valid @RequestBody Notification notification, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (notification.getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            notificationService.updateNotification(notification);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete notifications(for all admins and notification's author)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNotification(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (getNotification(id).getBody().getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            notificationService.deleteNotificationById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
