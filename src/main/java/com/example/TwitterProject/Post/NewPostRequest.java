package com.example.TwitterProject.Post;

public class NewPostRequest {
    private String postBody;
    private int userID;

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public int getUser_id() {
        return userID;
    }

    public void setUser_id(int user_id) {
        this.userID = user_id;
    }
}
