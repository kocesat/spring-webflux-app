package com.kocesat.reaactiveref.filetransfer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleFileUploadResponse {

  private boolean success;
  private String message;
  private String fileId;

  public static final SingleFileUploadResponse success(String fileId) {
    return SingleFileUploadResponse.builder()
      .fileId(fileId)
      .success(true)
      .message("Successfull")
      .build();
  }

  public static final SingleFileUploadResponse fail(String message) {
    return SingleFileUploadResponse.builder()
      .success(false)
      .message(message)
      .build();
  }
}
