package com.example.TwitterProject.Comment;

import com.example.TwitterProject.ErrorResponse;
import com.example.TwitterProject.Exception.CommentNotFoundException;
import com.example.TwitterProject.Exception.PostNotFoundException;
import com.example.TwitterProject.Exception.UserNotFoundException;
import com.example.TwitterProject.Post.Post;
import com.example.TwitterProject.Post.PostRepository;
import com.example.TwitterProject.User.UserAccount;
import com.example.TwitterProject.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userAccountRepository;
    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest createRequest) {
        try {
            UserAccount userAccount = userAccountRepository.findById(createRequest.getUserID())
                    .orElseThrow(() -> new UserNotFoundException("User does not exist"));

            Post post = postRepository.findById(createRequest.getPostID())
                    .orElseThrow(() -> new PostNotFoundException("Post does not exist"));

            Comment newComment = new Comment();
            newComment.setCommentBody(createRequest.getCommentBody());
            newComment.setPost(post);
            newComment.setUserAccount(userAccount);

            commentRepository.save(newComment);
            return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
        } catch (UserNotFoundException | PostNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getCommentById(@RequestParam long CommentID) {
        try {
            Comment comment = commentRepository.findById(CommentID).orElseThrow(() -> new CommentNotFoundException("Comment does not exist"));
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (CommentNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestBody retrieveCommentRequest commentDelete) {
        try {
            if (!commentRepository.existsById(commentDelete.getId())) {
                throw new CommentNotFoundException("Comment does not exist");
            }
            commentRepository.deleteById(commentDelete.getId());
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } catch (CommentNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody UpdateComment updatedComment) {
        try {
            return commentRepository.findById(updatedComment.getCommentId()).map(comment -> {
                comment.setCommentBody(updatedComment.getCommentBody());
                commentRepository.save(comment);
                return new ResponseEntity<>("Comment edited successfully", HttpStatus.OK);
            }).orElseThrow(() -> new CommentNotFoundException("Comment does not exist"));
        } catch (CommentNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}