package com.timemanagement.timemanagementrest.service;

import com.timemanagement.timemanagementrest.domain.Post;
import com.timemanagement.timemanagementrest.domain.dto.PostDTO;
import com.timemanagement.timemanagementrest.exception.PostNotFoundException;
import com.timemanagement.timemanagementrest.repository.PostRepository;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final SecurityService securityService;
    private final Post post;

    @Autowired
    public PostService(PostRepository postRepository, SecurityService securityService, Post post) {
        this.postRepository = postRepository;
        this.securityService = securityService;
        this.post = post;
    }

    public List<Post> getPosts() {
        return postRepository.findAllPosts();
    }

    public Optional<Post> getPost(Long id) {
        return postRepository.findByIdPost(id);
    }

    public void createPost(PostDTO postDTO, Principal principal) {
        post.setId(postRepository.getNextSequenceValue());
        post.setCreatedAt(LocalDateTime.now());
        post.setLikes(0L);
        post.setDislikes(0L);
        post.setComments(0L);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUserId(securityService.getUserIdByLogin(principal.getName()));
        postRepository.savePost(post);
    }

    public void updatePost(Post post) {
        postRepository.saveAndFlushPost(post);
    }

    public void putLike(Long id) {
        Post post = getPost(id).orElseThrow(PostNotFoundException::new);
        post.setLikes(post.getLikes()+1);
        postRepository.saveAndFlushPost(post);
    }
    public void putDislike(Long id) {
        Post post = getPost(id).orElseThrow(PostNotFoundException::new);
        post.setDislikes(post.getDislikes()+1);
        postRepository.saveAndFlushPost(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteByIdPost(id);
    }

}