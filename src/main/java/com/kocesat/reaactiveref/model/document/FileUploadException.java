package com.kocesat.reaactiveref.model.document;

public class FileUploadException extends RuntimeException{

  private String errorCode;

  public FileUploadException(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
