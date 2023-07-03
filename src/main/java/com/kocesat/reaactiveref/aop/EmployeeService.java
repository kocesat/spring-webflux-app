package com.kocesat.reaactiveref.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

  public Mono<EmployeeResponse> getEmployee(EmployeeRequest input) {
    log.info("ReactiveCall() made");
    return Mono.just(EmployeeResponse
      .builder()
      .name("out")
      .success(true)
      .build());
  }
}
