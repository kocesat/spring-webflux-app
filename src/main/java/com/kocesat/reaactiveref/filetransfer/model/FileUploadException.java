package com.kocesat.reaactiveref.filetransfer.model;

public class FileUploadException extends RuntimeException{

  private String errorCode;

  public FileUploadException(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
