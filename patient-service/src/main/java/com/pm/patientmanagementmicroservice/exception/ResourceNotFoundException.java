package com.pm.patientmanagementmicroservice.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

}
