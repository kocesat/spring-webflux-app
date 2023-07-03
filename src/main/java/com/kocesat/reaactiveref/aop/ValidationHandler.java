package com.kocesat.reaactiveref.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ValidationHandler {

  private final ValidationService validationService;

  @Around("@annotation(validateThisCall)")
  public Mono<Object> handle(ProceedingJoinPoint joinPoint, ValidateThisCall validateThisCall) throws Throwable{
    log.info("ValidationHandler intercepts before method call for: " + joinPoint.getSignature().getName());
    var request = (EmployeeRequest) joinPoint.getArgs()[0];
    return validationService.isValid(request.getValidationType())
      .flatMap(isValid -> {
        if (Boolean.FALSE.equals(isValid)) {
          return Mono.error(new IllegalArgumentException("Custom error"));
        }
        return proceed(joinPoint);
      });
  }

  private static Mono<Object> proceed(ProceedingJoinPoint joinPoint) {
    try {
      return (Mono<Object>) joinPoint.proceed();
    } catch (Throwable e) {
      log.error("Unexpected error: ", e);
      throw new RuntimeException(e);
    }
  }
}
