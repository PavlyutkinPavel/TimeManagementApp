package com.timemanagement.timemanagementrest.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(){
        super("Comment not found");
    }
}
