/*
 * Copyright (c) 2018. ceosilvajr All rights reserved
 */

package com.ceosilvajr.microserviceauth.retrofit;

import com.ceosilvajr.microserviceauth.config.MicroServiceConfig;
import com.ceosilvajr.microserviceauth.jwt.Payload;
import com.ceosilvajr.microserviceauth.jwt.PayloadEncoder;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.appengine.UrlFetchClient;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public final class RetrofitProvider {

  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private final String endpoint;
  private final String dateTimeFormat;

  public RetrofitProvider(final Builder builder) {
    this.endpoint = builder.endpoint;
    this.dateTimeFormat = builder.dateTimeFormat;
  }

  public RestAdapter initializeRestAdapter() {
    return new RestAdapter.Builder().setEndpoint(endpoint)
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setClient(new UrlFetchClient())
        .setConverter(new DynamicJsonConverter(new GsonBuilder().setDateFormat(dateTimeFormat).create()))
        .setRequestInterceptor(
            request -> request.addHeader(MicroServiceConfig.SERVICE_AUTH_HEADER_NAME.getValue(), serviceToken()))
        .build();
  }

  private String serviceToken() {
    return PayloadEncoder.instanceOf(MicroServiceConfig.SERVICE_SECRET_KEY.getValue())
        .encode(new Payload.Builder(MicroServiceConfig.SERVICE_APPID.getValue(),
            MicroServiceConfig.SERVICE_APPKEY.getValue()).build());
  }

  public static class Builder {

    private final String endpoint;
    private String dateTimeFormat;

    public Builder(final String endpoint) {
      this.endpoint = endpoint;
      this.dateTimeFormat = RetrofitProvider.DATE_TIME_FORMAT;
    }

    public Builder setDateTimeFormat(final String dateTimeFormat) {
      this.dateTimeFormat = dateTimeFormat;
      return this;
    }

    public RetrofitProvider build() {
      return new RetrofitProvider(this);
    }
  }
}
