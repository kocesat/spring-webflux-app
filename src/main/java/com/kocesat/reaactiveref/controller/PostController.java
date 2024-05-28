package com.kocesat.reaactiveref.controller;

import com.kocesat.reaactiveref.client.post.PostResponse;
import com.kocesat.reaactiveref.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostController {
  private final PostService postService;

  @GetMapping
  public Mono<PostResponse> getPosts() {
    return postService.getPosts();
  }
}
