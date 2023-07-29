package com.kocesat.reaactiveref.client.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StudentClient {
  public static final String LIST_URI = "/list";
  private final WebClient studentWebClient;
  public Mono<StudentServerResponse> getStudents(StudentServerRequest serverRequest) {
    return studentWebClient.post()
      .uri(LIST_URI)
      .body(BodyInserters.fromValue(serverRequest))
      .retrieve()
      .bodyToMono(StudentServerResponse.class);
  }
}
