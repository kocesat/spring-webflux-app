package com.kocesat.reaactiveref.client.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kocesat.reaactiveref.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentServerResponse {
  private boolean hasMore;

  @JsonProperty("students")
  private List<Student> studentList;
}
