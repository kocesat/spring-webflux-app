package com.kocesat.reaactiveref.util.web.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
  private boolean success;
  private Object data;

  public static BaseResponse success(Object data) {
    var response = new BaseResponse();
    response.success = true;
    response.data = data;
    return response;
  }
}
