package com.example.TwitterProject.Post;

public class PostUpdateRequest {
    private int postID;
    private String postBody;

    public int getPostId() {
        return postID;
    }

    public void setPostId(int postId) {
        this.postID = postId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
