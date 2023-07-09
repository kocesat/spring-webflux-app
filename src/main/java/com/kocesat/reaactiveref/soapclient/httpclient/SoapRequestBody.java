package com.kocesat.reaactiveref.soapclient.httpclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SoapRequestBody<T> {

  @JsonProperty("ListOfContinentsByNameResponse")
  private T listOfContinentsByNameResponse;
}
