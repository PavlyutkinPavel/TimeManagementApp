package com.timemanagement.timemanagementrest.exception;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(){
        super("Notification not found");
    }
}
