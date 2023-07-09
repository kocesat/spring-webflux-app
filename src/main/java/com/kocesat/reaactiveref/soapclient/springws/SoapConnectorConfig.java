package com.kocesat.reaactiveref.soapclient.springws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConnectorConfig {
  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this is the package name specified in the <generatePackage> specified in
    // pom.xml
    marshaller.setContextPath("com.kocesat.reaactiveref.soapclient.model");
    return marshaller;
  }

  @Bean
  public SoapConnector continentSoapConnector(Jaxb2Marshaller marshaller) {
    SoapConnector client = new SoapConnector();
    client.setDefaultUri("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}
