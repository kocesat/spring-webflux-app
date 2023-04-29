package com.kocesat.reaactiveref.controller;

import com.kocesat.reaactiveref.model.BindingDto;
import com.kocesat.reaactiveref.service.BindingService;
import com.kocesat.reaactiveref.util.web.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("binding")
@RequiredArgsConstructor
@Slf4j
public class BindingController {
  private final BindingService service;

  @PostMapping
  public Mono<ResponseEntity<BaseResponse>> checkBinding(@RequestBody BindingDto dto) {
    var localDateTime = LocalDateTime.ofInstant(dto.getRequestTimeWithZone().toInstant(), ZoneId.systemDefault());
    log.info(localDateTime.toString());
    return service.handleBinding(dto)
      .flatMap(bindingDto -> {
        log.info(dto.toString());
        BaseResponse response = BaseResponse.success(bindingDto);
        return Mono.just(ResponseEntity.ok(response));
      });
  }
}
