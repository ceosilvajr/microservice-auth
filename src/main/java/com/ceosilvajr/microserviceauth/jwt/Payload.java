package com.ceosilvajr.microserviceauth.jwt;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public class Payload {

  private final String appId;
  private final String appKey;
  private final Platform platform;

  public Payload(final Builder builder) {
    this.appId = builder.appId;
    this.appKey = builder.appKey;
    this.platform = builder.platform;
  }

  public String getAppId() {
    return appId;
  }

  public String getAppKey() {
    return appKey;
  }

  public Platform getPlatform() {
    return platform;
  }

  public static class Builder {

    private final String appId;
    private final String appKey;
    private final Platform platform;

    public Builder(final String appId, final String appKey) {
      this.appId = appId;
      this.appKey = appKey;
      this.platform = Platform.SERVICE;
    }

    public Payload build() {
      return new Payload(this);
    }
  }
}
