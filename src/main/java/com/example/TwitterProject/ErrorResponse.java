package com.example.TwitterProject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    private String Error; // Changed from E to Error

    public ErrorResponse(String Error) {
        this.Error = Error;
    }
    @JsonProperty("Error")
    public String getError() { // Changed from getE to getError
        return Error;
    }

    public void setError(String Error) { // Changed from setE to setError
        this.Error = Error;
    }
}