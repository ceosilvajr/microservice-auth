package com.ceosilvajr.microserviceauth.config;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public enum MicroServiceConfig {

  SERVICE_AUTH_HEADER_NAME(System.getProperty("service.auth.name.header")),
  SERVICE_AUTH_REQUEST_NAME(System.getProperty("service.auth.name.request")),
  SERVICE_SECRET_KEY(System.getProperty("service.secretKey")),
  SERVICE_APPID(System.getProperty("service.appId")),
  SERVICE_APPKEY(System.getProperty("service.appKey")),
  SERVICE_APIKEY(System.getProperty("service.apiKey"));

  private final String value;

  MicroServiceConfig(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
