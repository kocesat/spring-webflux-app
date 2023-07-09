package com.kocesat.reaactiveref.soapclient;

import com.kocesat.reaactiveref.soapclient.httpclient.ContinentHttpClient;
import com.kocesat.reaactiveref.soapclient.model.ListOfContinentsByName;
import com.kocesat.reaactiveref.soapclient.model.ListOfContinentsByNameResponse;
import com.kocesat.reaactiveref.soapclient.springws.ContinentSoapClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("continent")
public class ContinentController {

  private final ContinentSoapClient continentSoapClient;
  private final ContinentHttpClient continentHttpClient;

  @GetMapping("/spring-ws")
  public Mono<ListOfContinentsByNameResponse> get() {
    return continentSoapClient.getContinents(new ListOfContinentsByName());
  }

  @GetMapping("/http-client")
  public ListOfContinentsByNameResponse getUsingHttpClient() {
    return continentHttpClient.getContinents();
  }
}
