package com.example.TwitterProject.Post;

import com.example.TwitterProject.Comment.CommentResponse;
import com.example.TwitterProject.ErrorResponse;
import com.example.TwitterProject.Exception.PostNotFoundException;
import com.example.TwitterProject.User.UserAccount;
import com.example.TwitterProject.User.UserRepository;
import com.example.TwitterProject.User.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody NewPostRequest newPostRequest) {
        UserAccount user = userRepository.findById(newPostRequest.getUserID()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }
        Post newPost = new Post();
        newPost.setPostBody(newPostRequest.getPostBody());
        Date currentDate = new Date();
        newPost.setDate(currentDate);
        newPost.setUserAccount(user);
        postRepository.save(newPost);
        return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPostById(@RequestParam int postID) {
        try {
            Post post = postRepository.findById(postID).orElseThrow(() -> new PostNotFoundException("Post does not exist"));

            PostResponse postResponse = new PostResponse();
            postResponse.setPostID(post.getPostID());
            postResponse.setPostBody(post.getPostBody());
            postResponse.setDate(post.getDate());

            List<CommentResponse> commentResponses = post.getComments().stream().map(comment -> {
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setCommentID(comment.getCommentID());
                commentResponse.setCommentBody(comment.getCommentBody());

                UserSummary userSummary = new UserSummary();
                userSummary.setUserID(comment.getUserAccount().getId());
                userSummary.setName(comment.getUserAccount().getName());

                commentResponse.setCommentCreator(userSummary);
                return commentResponse;
            }).collect(Collectors.toList());

            postResponse.setComments(commentResponses);

            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        } catch (PostNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/post")
    public ResponseEntity<?> updatePost(@RequestBody PostUpdateRequest updatedPost) {
        try {
            return postRepository.findById(updatedPost.getPostID()).map(post -> {
                post.setPostBody(updatedPost.getPostBody());
                postRepository.save(post);
                return new ResponseEntity<>("Post edited successfully", HttpStatus.OK);
            }).orElseThrow(() -> new PostNotFoundException("Post does not exist"));
        } catch (PostNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam int postID) {
        try {
            if (!postRepository.existsById(postID)) {
                throw new PostNotFoundException("Post does not exist");
            }
            postRepository.deleteById(postID);
            return new ResponseEntity<>("Post deleted", HttpStatus.OK);
        } catch (PostNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByDateDesc();

        return posts.stream().map(post -> {
            PostResponse postResponse = new PostResponse();
            postResponse.setPostID(post.getPostID());
            postResponse.setPostBody(post.getPostBody());
            postResponse.setDate(post.getDate());

            List<CommentResponse> commentResponses = post.getComments().stream().map(comment -> {
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setCommentID(comment.getCommentID());
                commentResponse.setCommentBody(comment.getCommentBody());

                UserSummary userSummary = new UserSummary();
                userSummary.setUserID(comment.getUserAccount().getId());
                userSummary.setName(comment.getUserAccount().getName());

                commentResponse.setCommentCreator(userSummary);
                return commentResponse;
            }).collect(Collectors.toList());

            postResponse.setComments(commentResponses);
            return postResponse;
        }).collect(Collectors.toList());
    }
}

