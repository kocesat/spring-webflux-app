package com.kocesat.reaactiveref.filetransfer;

import com.kocesat.reaactiveref.filetransfer.model.FileUploadException;
import com.kocesat.reaactiveref.filetransfer.model.FileUploadResponse;
import com.kocesat.reaactiveref.filetransfer.model.SingleFileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class FileTransferService {

  @Value("#{${fileType}}")
  private Map<String, String> fileTypeMap;

  private static final Map<Integer, String> fileDomainMap = Map.of(1, "domain1", 2, "domain2");

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
    return getLocale()
      .flatMap(locale ->
        validateAndUpload(filePart, type)
          .onErrorResume(FileTransferService::handleBusinessError));
  }

  public Mono<SingleFileUploadResponse> validateAndUpload(FilePart filePart, Integer type) {
    if (!fileDomainMap.containsKey(type)) {
      return Mono.error(new FileUploadException("INVALID_TYPE"));
    }
    final String fileTypeCode = fileTypeMap.get(fileDomainMap.get(type));
    log.info("File Type code resolved: " + fileTypeCode);

    List<String> allowedExtensions = List.of("txt", "xlsx", "png", "jpg", "pdf", "pptx", "jpeg");
    String extension = FilenameUtils.getExtension(filePart.filename());
    if (allowedExtensions
      .stream()
      .noneMatch(allowedExt -> allowedExt.equalsIgnoreCase(extension))) {
      return Mono.error(new FileUploadException("INVALID_EXTENSION"));
    }
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

  private String getFileCode(String domain) {
    return fileTypeMap.get(domain);
  }

  private static Mono<String> getLocale() {
    return Mono.just("tr");
  }

  private static Mono<SingleFileUploadResponse> handleBusinessError(Throwable e) {
    SingleFileUploadResponse response = SingleFileUploadResponse.fail("Default Message");
    if (e instanceof FileUploadException fileUploadException) {
      response.setMessage(fileUploadException.getErrorCode());
    }
    return Mono.just(response);
  }
}
