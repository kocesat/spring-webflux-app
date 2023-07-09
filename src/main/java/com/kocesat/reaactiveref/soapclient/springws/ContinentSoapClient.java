package com.kocesat.reaactiveref.soapclient.springws;

import com.kocesat.reaactiveref.soapclient.model.ListOfContinentsByName;
import com.kocesat.reaactiveref.soapclient.model.ListOfContinentsByNameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContinentSoapClient {

  private final SoapConnector continentSoapConnector;

  public Mono<ListOfContinentsByNameResponse> getContinents(ListOfContinentsByName request) {
    return Mono.just(continentSoapConnector.call(request));
  }

}
