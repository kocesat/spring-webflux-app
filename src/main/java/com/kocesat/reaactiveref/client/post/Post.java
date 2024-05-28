package com.kocesat.reaactiveref.client.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  private Integer id;
  private Integer userId;
  private String title;
  private String body;
}
