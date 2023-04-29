package com.kocesat.reaactiveref.controller;

import com.kocesat.reaactiveref.model.Student;
import com.kocesat.reaactiveref.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("students")
public class StudentController {
  private final StudentService service;

  @GetMapping
  public Mono<List<Student>> getStudents(@RequestParam("page") int page,
                                         @RequestParam("pageSize") int pageSize) {
    if (page < 1) {
      page = 1;
    }
    return service.getStudents(page, pageSize);
  }
}
