package com.kocesat.reaactiveref.model.document;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class FileUploadResponse {
  List<String> results;
}
