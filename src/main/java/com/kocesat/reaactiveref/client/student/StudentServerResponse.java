package com.kocesat.reaactiveref.client.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kocesat.reaactiveref.model.Student;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentServerResponse {
  @Getter(AccessLevel.NONE)
  private boolean hasMore;
  @JsonProperty("students")
  private List<Student> studentList;

  @JsonProperty("devamFlag")
  public boolean hasMore() {
    return hasMore;
  }
}