package com.timemanagement.timemanagementrest.exception;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(){
        super("Review not found");
    }
}
