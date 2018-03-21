package com.ceosilvajr.microserviceauth.config;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public enum MicroServiceConfig {

  SERVICE_HEADER_NAME(System.getProperty("service.header.name")),
  SERVICE_SECRET_KEY(System.getProperty("service.secret.key")),
  SERVICE_APP_ID(System.getProperty("service.appId")),
  SERVICE_APP_KEY(System.getProperty("service.appKey"));

  private final String value;

  MicroServiceConfig(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
