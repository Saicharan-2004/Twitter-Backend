package com.example.TwitterProject.Post;

public class PostUpdateRequest {
    private int postID;
    private String postBody;

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postId) {
        this.postID = postId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
