package com.kocesat.reaactiveref.service;

import com.kocesat.reaactiveref.model.FileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileTransferService {

  public Flux<byte[]> getDownloadableFile() {
    Resource file = new ClassPathResource("files/example-file.txt");
    byte[] bytes = null;
    try (InputStream is = file.getInputStream()) {
      bytes = is.readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return Flux.just(bytes);
  }

  public Mono<FileUploadResponse> upload(List<FilePart> fileParts) {
    List<String> results = new ArrayList<>();

    return Mono.just(fileParts).flatMap(fpList -> {
      fpList.forEach(fp -> {
        fp.transferTo(Paths.get("/Users/kocesat/Desktop/temp", fp.filename()).toFile()).then();
        results.add(fp.filename());
      });
      return Mono.just(FileUploadResponse.builder().results(results).build());
    });
  }
}
