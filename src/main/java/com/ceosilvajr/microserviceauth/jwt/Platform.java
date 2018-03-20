package com.ceosilvajr.microserviceauth.jwt;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public enum Platform {

  SERVICE("SERVICE");

  private final String value;

  Platform(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
