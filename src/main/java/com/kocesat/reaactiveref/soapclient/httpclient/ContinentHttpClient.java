package com.kocesat.reaactiveref.soapclient.httpclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kocesat.reaactiveref.soapclient.model.ContinentsResponse;
import com.kocesat.reaactiveref.soapclient.model.ListOfContinentsByNameResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContinentHttpClient {

  public ListOfContinentsByNameResponse getContinents() {
    String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
      "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
      "  <soap12:Body>\n" +
      "    <ListOfContinentsByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
      "    </ListOfContinentsByName>\n" +
      "  </soap12:Body>\n" +
      "</soap12:Envelope>";
    try {
      var httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.of(20, ChronoUnit.SECONDS))
        .build();
      var httpRequest = HttpRequest
        .newBuilder(new URI("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso"))
        .header("Content-Type", "text/xml; charset=utf-8")
        .POST(HttpRequest.BodyPublishers.ofString(requestXml))
        .build();
      HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
      checkStatus(httpResponse);
      return resolve(httpResponse.body());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  @SneakyThrows
  private static ListOfContinentsByNameResponse resolve(String xmlString) {
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    var responseObject = xmlMapper.readValue(xmlString, new TypeReference<SoapEnvelope<ContinentsResponse>>() {
    });
      return responseObject.getSoapBody().getListOfContinentsByNameResponse();
  }

  private static void checkStatus(HttpResponse<String> httpResponse) {
    if (httpResponse.statusCode() != HttpStatus.OK.value()) {
      log.error(httpResponse.body());
      throw new RuntimeException("Unsuccessful Http call, returns with status code: " + httpResponse.statusCode());
    }
  }

  private static void resolveBody(String responseXml) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder documentBuilder = factory.newDocumentBuilder();
      Document document = documentBuilder.parse(new InputSource(new StringReader(responseXml)));
      document.getDocumentElement().normalize();

      XPathFactory xPathFactory = XPathFactory.newInstance();
      XPath xPath = xPathFactory.newXPath();
      XPathExpression expression = xPath.compile("//Envelope//Body/text()");
      String result = expression.evaluate(document);
      System.out.println(result);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException("Error resolving soap body");
    }
  }
}
