package com.example.TwitterProject.Post;

public class NewPostRequest {
    private String postBody;
    private int userID; // Changed from user_id to userID

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public int getUserID() { // Changed from getUser_id to getUserID
        return userID;
    }

    public void setUserID(int userID) { // Changed from setUser_id to setUserID
        this.userID = userID;
    }
}