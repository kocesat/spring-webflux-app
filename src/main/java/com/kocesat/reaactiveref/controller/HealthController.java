package com.kocesat.reaactiveref.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("health")
public class HealthController {

  @GetMapping("/check")
  public Mono<String> healthCheck() {
    return Mono.just("Health Check O.K");
  }
}
