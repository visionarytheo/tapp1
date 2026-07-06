package com.tapp.tserver.dto;

public record ResDto(String status, String message, Object data) {

    public static ResDto success(String message, Object data) {
        return new ResDto("success", message, data);
    }
    public static ResDto success(String message) {
        return new ResDto("success", message, null);
    }
    public static ResDto error(String message) {
        return new ResDto("error", message, null);
    }
    
}
