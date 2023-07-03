package com.kocesat.reaactiveref.aop;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ValidationService {

  public Mono<Boolean> isValid(Integer type) {
    if (type == 1) {
      return Mono.just(true);
    }
    return Mono.just(false);
  }

}
