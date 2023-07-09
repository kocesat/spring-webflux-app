package com.kocesat.reaactiveref.soapclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContinentsResponse {

  @JsonProperty("ListOfContinentsByNameResponse")
  private ListOfContinentsByNameResponse listOfContinentsByNameResponse;
}
