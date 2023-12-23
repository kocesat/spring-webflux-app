package com.kocesat.reaactiveref.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CategoryPropertyService {

  @Value("#{${category.map}}")
  private Map<String, String> categoryMap;

  public Mono<String> getCategory(String key) {
    return Mono.just(categoryMap.get(key));
  }

}
