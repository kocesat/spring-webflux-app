package com.kocesat.reaactiveref.controller;

import com.kocesat.reaactiveref.model.BindingDto;
import com.kocesat.reaactiveref.service.BindingService;
import com.kocesat.reaactiveref.util.web.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("binding")
@RequiredArgsConstructor
@Slf4j
public class BindingController {
  private final BindingService service;

  @PostMapping
  public Mono<ResponseEntity<BaseResponse>> checkBinding(@RequestBody BindingDto dto) {
    return service.handleBinding(dto)
      .flatMap(bindingDto -> {
        log.info(dto.toString());
        BaseResponse response = BaseResponse.success(bindingDto);
        return Mono.just(ResponseEntity.ok(response));
      });
  }
}
