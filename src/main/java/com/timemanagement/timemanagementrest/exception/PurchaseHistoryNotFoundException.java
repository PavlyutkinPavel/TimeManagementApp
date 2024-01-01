package com.timemanagement.timemanagementrest.exception;

public class PurchaseHistoryNotFoundException extends RuntimeException{
    public PurchaseHistoryNotFoundException(){
        super("PurchaseHistory not found");
    }
}
