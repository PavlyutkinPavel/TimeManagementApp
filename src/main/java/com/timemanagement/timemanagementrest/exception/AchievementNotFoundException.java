package com.timemanagement.timemanagementrest.exception;

public class AchievementNotFoundException extends RuntimeException{
    public AchievementNotFoundException(){
        super("Achievement not found");
    }
}
