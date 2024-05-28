package com.kocesat.reaactiveref.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;

@Service
@Slf4j
public class MailService {

  @SneakyThrows
  public Integer sendMail() {
    log.info("Starting to send mail...");
    Thread.sleep(3000);
    log.info("Finished sending mail...");
    return 1;
  }

  public void sendAsyncMail() {
    ForkJoinPool.commonPool().submit(this::sendMail);
  }
}
