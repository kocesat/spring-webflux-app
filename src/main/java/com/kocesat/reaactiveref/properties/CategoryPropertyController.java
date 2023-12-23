package com.kocesat.reaactiveref.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category-property")
@RequiredArgsConstructor
public class CategoryPropertyController {

  private final CategoryPropertyService service;

  @GetMapping
  public Mono<String> getCategory(@RequestParam("key") String key) {
    return service.getCategory(key);
  }
}
