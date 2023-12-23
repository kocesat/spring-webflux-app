package com.kocesat.reaactiveref.service;

import com.kocesat.reaactiveref.model.document.FileUploadException;
import com.kocesat.reaactiveref.model.document.FileUploadResponse;
import com.kocesat.reaactiveref.model.document.SingleFileUploadResponse;
import io.netty.handler.codec.http.multipart.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

  public Mono<SingleFileUploadResponse> singleUpload(Integer type, FilePart filePart) {
    return authMono()
      .flatMap(x -> {
        List<Integer> acceptedTypes = List.of(1, 2);
        List<String> allowedExtensions = List.of("txt", "xlsx", "png", "jpg", "PDF", "pptx");
        if (!acceptedTypes.contains(type)) {
          return Mono.error(new FileUploadException("INVALID_TYPE"));
        }
        String extension = FilenameUtils.getExtension(filePart.filename());
        if (allowedExtensions
            .stream()
            .noneMatch(allowedExt -> allowedExt.equalsIgnoreCase(extension))) {
          return Mono.error(new FileUploadException("INVALID_EXTENSION"));
        }
        return doUpload(filePart);
      })
      .onErrorResume(e -> {
        SingleFileUploadResponse response = SingleFileUploadResponse.fail("Default Message");
        if (e instanceof FileUploadException fileUploadException) {
          response.setMessage(fileUploadException.getErrorCode());
        }
        return Mono.just(response);
      });
  }

  public Mono<SingleFileUploadResponse> doUpload(FilePart filePart) {
    return DataBufferUtils.join(filePart.content())
      .map(dataBuffer -> {
        int size = dataBuffer.readableByteCount();
        if (size > 1024 * 1024 * 3) {
          DataBufferUtils.release(dataBuffer);
          throw new FileUploadException("INVALID_FILE_SIZE");
        }
        byte[] bytes = new byte[size];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);
        try (OutputStream os = new FileOutputStream(new File("/Users/kocesat/Desktop/temp/uploaded/" + filePart.filename()))) {
          os.write(bytes);
        } catch (IOException e) {
          log.error(e.getMessage(), e);
          throw new RuntimeException(e);
        }
        return SingleFileUploadResponse.success(UUID.randomUUID().toString());
      });
  }

  private static Mono<String> authMono() {
    return Mono.just("");
  }
}
