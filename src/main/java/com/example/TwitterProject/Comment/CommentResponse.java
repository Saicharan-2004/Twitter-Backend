package com.example.TwitterProject.Comment;

import com.example.TwitterProject.User.UserSummary;

public class CommentResponse {
    private int commentID;
    private String commentBody;
    private UserSummary commentCreator;

    // getters and setters

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public UserSummary getCommentCreator() {
        return commentCreator;
    }

    public void setCommentCreator(UserSummary commentCreator) {
        this.commentCreator = commentCreator;
    }
}