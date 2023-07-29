package com.kocesat.reaactiveref.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kocesat.reaactiveref.client.student.StudentServerResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class StudentServerResponseTest {

  private final ObjectMapper objectMapper = (new ObjectMapper())
    .setSerializationInclusion(JsonInclude.Include.NON_NULL);
  @Test
  @SneakyThrows
  void serialize() {
    final var model = StudentServerResponse.builder()
      .hasMore(true)
      .studentList(List.of(
        Student.builder().name("ferman").build(),
        Student.builder().name("esat").build()
      ))
      .build();
    final var actual = objectMapper.writeValueAsString(model);
    final var expected = "{\"devamFlag\":true,\"students\":[{\"name\":\"ferman\"},{\"name\":\"esat\"}]}";
    Assertions.assertEquals(expected, actual);
    System.out.println(actual);
  }

  @Test
  @SneakyThrows
  void deserialize() {
    final String jsonString = "{\"devamFlag\":true,\"students\":[{\"name\":\"ferman\"},{\"name\":\"esat\"}]}";
    final StudentServerResponse actual = objectMapper.readValue(jsonString, StudentServerResponse.class);
    final var expected = StudentServerResponse.builder()
      .hasMore(true)
      .studentList(List.of(
        Student.builder().name("ferman").build(),
        Student.builder().name("esat").build()
      ))
      .build();
    Assertions.assertEquals(expected, actual);
    System.out.println(actual);
  }
}
