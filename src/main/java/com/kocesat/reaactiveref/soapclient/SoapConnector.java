package com.kocesat.reaactiveref.soapclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Slf4j
public class SoapConnector extends WebServiceGatewaySupport {

  public <T, E> E call(T request) {
    log.info("Calling soap service with request: " + request);
    return (E)getWebServiceTemplate().marshalSendAndReceive(request);
  }
}
