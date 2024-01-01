package com.timemanagement.timemanagementrest.exception;

public class RequestBodyNotFullException extends RuntimeException{
    public RequestBodyNotFullException(){
        super("Request body must have all needed fields");
    }

    public static String getDefaultMessage(){
        return "Request body must have all needed fields";
    }
}
