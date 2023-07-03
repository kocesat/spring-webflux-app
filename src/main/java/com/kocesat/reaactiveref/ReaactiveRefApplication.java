package com.kocesat.reaactiveref;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class ReaactiveRefApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReaactiveRefApplication.class, args);
  }

}
