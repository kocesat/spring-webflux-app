package com.kocesat.reaactiveref.controller;

import com.kocesat.reaactiveref.model.FileUploadResponse;
import com.kocesat.reaactiveref.service.FileTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
@Slf4j
public class FileTransferController {
  private final FileTransferService fileTransferService;

  @PostMapping("/upload")
  public Mono<FileUploadResponse> upload(@RequestPart List<FilePart> files){
    return fileTransferService.upload(files);
  }

  @GetMapping("/download")
  public Flux<DataBuffer> download(ServerWebExchange exchange) {
    return fileTransferService.getDownloadableFile()
      .flatMap(content -> {
        var headers = exchange.getResponse().getHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("example.txt").build());
        DataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(content);
        return Flux.just(dataBuffer);
      }).doOnNext(response -> {
        log.info("File downloaded successfully");
      });
  }
}
