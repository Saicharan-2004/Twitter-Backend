package com.example.TwitterProject;

public class ErrorResponse {
    private String Error;

    public ErrorResponse(String error) {
        Error = error;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}