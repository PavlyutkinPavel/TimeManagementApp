package com.timemanagement.timemanagementrest.exception;

public class LabelNotFoundException extends RuntimeException{
    public LabelNotFoundException(){
        super("Label not found");
    }
}
