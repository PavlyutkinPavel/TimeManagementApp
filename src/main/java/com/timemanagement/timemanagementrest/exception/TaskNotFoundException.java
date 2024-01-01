package com.timemanagement.timemanagementrest.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(){
        super("User not found");
    }
}
