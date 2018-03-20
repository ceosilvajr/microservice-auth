package com.ceosilvajr.microserviceauth.retrofit;

import com.ceosilvajr.microserviceauth.config.AppConfig;
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
  private final RestAdapter.LogLevel logLevel;

  public RetrofitProvider(final Builder builder) {
    this.endpoint = builder.endpoint;
    this.dateTimeFormat = builder.dateTimeFormat;
    this.logLevel = builder.logLevel;
  }

  public RestAdapter initializeRestAdapter() {
    return new RestAdapter.Builder().setEndpoint(endpoint)
        .setLogLevel(logLevel)
        .setClient(new UrlFetchClient())
        .setConverter(new DynamicJsonConverter(new GsonBuilder().setDateFormat(dateTimeFormat).create()))
        .setRequestInterceptor(request -> request.addHeader(AppConfig.SERVICE_HEADER_NAME.getValue(), serviceToken()))
        .build();
  }

  private String serviceToken() {
    return PayloadEncoder.instanceOf(AppConfig.SERVICE_SECRET_KEY.getValue())
        .encode(new Payload.Builder(AppConfig.SERVICE_APP_ID.getValue(), AppConfig.SERVICE_APP_KEY.getValue()).build());
  }

  public static class Builder {

    private final String endpoint;
    private String dateTimeFormat;
    private RestAdapter.LogLevel logLevel;

    public Builder(final String endpoint) {
      this.endpoint = endpoint;
      this.dateTimeFormat = RetrofitProvider.DATE_TIME_FORMAT;
      this.logLevel = RestAdapter.LogLevel.FULL;
    }

    public Builder setDateTimeFormat(final String dateTimeFormat) {
      this.dateTimeFormat = dateTimeFormat;
      return this;
    }

    public Builder setLogLevel(final RestAdapter.LogLevel logLevel) {
      this.logLevel = logLevel;
      return this;
    }

    public RetrofitProvider build() {
      return new RetrofitProvider(this);
    }
  }
}
