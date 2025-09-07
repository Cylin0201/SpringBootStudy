package com.backend.ureca.cylin0201.startspring.post.controller;

import com.backend.ureca.cylin0201.startspring.post.domain.Post;
import com.backend.ureca.cylin0201.startspring.post.dto.PostRequest;
import com.backend.ureca.cylin0201.startspring.post.service.PostService;
import com.backend.ureca.cylin0201.startspring.user.dto.PostResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    ResponseEntity<PostResponse> upload(@RequestBody PostRequest req){
        return ResponseEntity.ok(postService.uploadPost(req));
    }


}
