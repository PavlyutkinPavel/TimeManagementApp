package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Comment;
import com.timemanagement.timemanagementrest.domain.dto.CommentDTO;
import com.timemanagement.timemanagementrest.exception.CommentNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Comment Controller", description = "makes all operations with comments")
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final SecurityService securityService;

    public CommentController(CommentService commentService, SecurityService securityService) {
        this.commentService = commentService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all comments(for all authorized users)")
    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        List<Comment> comments = commentService.getComments();
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
    }

    @Operation(summary = "get all comments for some post(for all authorized users)")
    @GetMapping("/post_comments")
    public ResponseEntity<List<Comment>> getPostComments(@RequestParam Long postId) {
        List<Comment> comments = commentService.getPostComments(postId);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
    }

    @Operation(summary = "get specific comment(for all authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
        Comment comment = commentService.getComment(id).orElseThrow(CommentNotFoundException::new);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }



    @Operation(summary = "create comment(for all authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createComment(@Valid @RequestBody CommentDTO commentDTO, Principal principal) {
        commentService.createComment(commentDTO, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update comments(for all admins and comment's author)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateComment(@Valid @RequestBody Comment comment, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (comment.getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            commentService.updateComment(comment);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete comments(for all admins and comment's author)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (getComment(id).getBody().getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            commentService.deleteCommentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
