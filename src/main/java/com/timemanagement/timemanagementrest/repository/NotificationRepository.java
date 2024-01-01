package com.timemanagement.timemanagementrest.repository;

import com.timemanagement.timemanagementrest.domain.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM notifications WHERE id = :notificationId")
    Optional<Notification> findByIdNotification(@Param("notificationId") Long notificationId);

    @Query(nativeQuery = true, value = "SELECT * FROM notifications")
    List<Notification> findAllNotifications();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO notifications(id, user_id, notifications_message, created_at) VALUES (:#{#notification.id}, :#{#notification.userId}, :#{#notification.message}, :#{#notification.created_at})")
    void saveNotification(@Param("notification") Notification notification);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE notifications SET user_id = :#{#notification.userId},  notifications_message = :#{#notification.message}, created_at = :#{#notification.created_at} WHERE id = :#{#notification.id}")
    void saveAndFlushNotification(@Param("notification") Notification notification);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM notifications WHERE id = :notificationId")
    void deleteByIdNotification(@Param("notificationId") Long notificationId);

    @Query(value = "SELECT NEXTVAL('notifications_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}

