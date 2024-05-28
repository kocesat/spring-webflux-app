package com.kocesat.reaactiveref.service;

import com.kocesat.reaactiveref.client.post.PostClient;
import com.kocesat.reaactiveref.client.post.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

  private final MailService mailService;
  private final PostClient postClient;

  public Mono<PostResponse> getPosts() {
    log.info("Starting to fetch posts...");
    return postClient.getPosts()
        .doOnNext(postResponse -> {
          if (postResponse.getPostList().size() > 20) {
            Mono.fromRunnable(mailService::sendMail)
                .subscribeOn(Schedulers.parallel())
                .subscribe();
          }
          log.info("Finished fetching posts...");
        });
  }
}
