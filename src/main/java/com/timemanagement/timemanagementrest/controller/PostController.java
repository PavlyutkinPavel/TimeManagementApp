package com.timemanagement.timemanagementrest.controller;

import com.timemanagement.timemanagementrest.domain.Post;
import com.timemanagement.timemanagementrest.domain.dto.PostDTO;
import com.timemanagement.timemanagementrest.exception.PostNotFoundException;
import com.timemanagement.timemanagementrest.security.service.SecurityService;
import com.timemanagement.timemanagementrest.service.PostService;
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
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Post Controller", description = "Makes all operations with posts")
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final SecurityService securityService;

    private static HashMap<String, Long> statistics = new HashMap<>();

    public PostController(PostService postService, SecurityService securityService) {
        this.postService = postService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all posts(for authorized users)")
    @GetMapping
    public ResponseEntity<List<Post>> getPosts(Principal principal) {
        List<Post> posts = postService.getPosts();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
    }

    @Operation(summary = "get post(for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id).orElseThrow(PostNotFoundException::new);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @Operation(summary = "create post(for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createPost(@Valid @RequestBody PostDTO postDTO, Principal principal) {
        postService.createPost(postDTO, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "create post(for creator)")
    @PutMapping
    public ResponseEntity<HttpStatus> updatePost(@RequestBody Post post, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (post.getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            postService.updatePost(post);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete post(for creator)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (getPost(id).getBody().getUserId() == securityService.getUserIdByLogin(principal.getName()))) {
            postService.deletePostById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get post statistics(for all users)")
    @GetMapping("/statistics/{id}")
    public ResponseEntity<HashMap<String, Long>> getPostStatistics(@PathVariable Long id) {
        Post post = postService.getPost(id).orElseThrow(PostNotFoundException::new);

        statistics.put("Likes: ", post.getLikes());
        statistics.put("Dislikes: ", post.getDislikes());
        statistics.put("Comments: ", post.getComments());

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @Operation(summary = "put like to post(for all users)")
    @PutMapping("/like/{id}")
    public ResponseEntity<HashMap<String, Long>> putLike(@PathVariable Long id) {
        postService.putLike(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "put dislike to post(for all users)")
    @PutMapping("/dislike/{id}")
    public ResponseEntity<HashMap<String, Long>> putDislike(@PathVariable Long id) {
        postService.putDislike(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}