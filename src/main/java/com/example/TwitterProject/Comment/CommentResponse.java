package com.example.TwitterProject.Comment;

import com.example.TwitterProject.User.UserSummary;

public class CommentResponse {
    private Long commentID;
    private String commentBody;
    private UserSummary commentCreator;

    // getters and setters

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
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