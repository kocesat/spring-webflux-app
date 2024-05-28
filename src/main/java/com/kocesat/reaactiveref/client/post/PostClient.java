package com.kocesat.reaactiveref.client.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostClient {

  private static final String PATH = "/posts";
  private final WebClient jsonPlaceholderWebClient;

  public Mono<PostResponse> getPosts() {
    log.info("PostClient runs");
    return jsonPlaceholderWebClient.get()
        .uri(PATH)
        .exchangeToMono(clientResponse -> {
          var typeRef = new ParameterizedTypeReference<List<Post>>() {};
          return clientResponse.bodyToMono(typeRef)
              .flatMap(x -> Mono.just(PostResponse.builder().postList(x).build()));
        })
        .onErrorResume(t -> {
          log.error(t.getMessage(), t);
          return Mono.error(t);
        });
  }

}
