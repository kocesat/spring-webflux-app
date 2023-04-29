package com.kocesat.reaactiveref.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
  private Long id;
  private String name;
  private LocalDateTime created;
}
