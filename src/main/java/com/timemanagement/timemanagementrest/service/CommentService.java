package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Comment;
import com.timemanagement.timemanagementrest.domain.dto.CommentDTO;
import com.timemanagement.timemanagementrest.repository.CommentRepository;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final Comment comment;
    private final SecurityService securityService;

    @Autowired
    public CommentService(CommentRepository commentRepository, Comment comment, SecurityService securityService) {
        this.commentRepository = commentRepository;
        this.comment = comment;
        this.securityService = securityService;
    }

    public List<Comment> getComments() {
        return commentRepository.findAllComments();
    }

    public List<Comment> getPostComments(Long postId) {
        List<Comment> commentList = commentRepository.findAllComments();
        List<Comment> newCommentList = new ArrayList<>(){};
        for (Comment comment : commentList) {
            if(comment.getPostId() ==  postId){
                newCommentList.add(comment);
            }
        }
        return newCommentList;
    }

    public Optional<Comment> getComment(Long id) {
        return commentRepository.findByIdComment(id);
    }

    public void createComment(CommentDTO commentDTO, Principal principal) {
        comment.setId(commentRepository.getNextSequenceValue());
        comment.setTitle(commentDTO.getTitle());
        comment.setPostId(commentDTO.getPostId());
        comment.setUserId(securityService.getUserIdByLogin(principal.getName()));
        commentRepository.saveComment(comment);
    }

    public void updateComment(Comment comment) {
        commentRepository.saveAndFlushComment(comment);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteByIdComment(id);
    }

}