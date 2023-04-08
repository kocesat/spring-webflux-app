package com.kocesat.reaactiveref.service;

import com.kocesat.reaactiveref.model.BindingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BindingService {

  public Mono<BindingDto> handleBinding(BindingDto dto) {
    log.info(dto.toString());
    return Mono.just(dto);
  }
}
