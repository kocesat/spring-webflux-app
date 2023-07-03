package com.kocesat.reaactiveref.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @ValidateThisCall(type = 1)
  @PostMapping
  public Mono<EmployeeResponse> get(@RequestBody EmployeeRequest request) {
    return employeeService.getEmployee(request);
  }
}
