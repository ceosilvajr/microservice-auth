package com.ceosilvajr.microserviceauth;

import com.ceosilvajr.microserviceauth.retrofit.RetrofitProvider;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public class MicroServiceAuth<T> {

  private final String endpoint;

  public MicroServiceAuth(final String endpoint) {
    this.endpoint = endpoint;
  }

  public T provideDefaultApiClass(final Class<T> apiClass) {
    return new RetrofitProvider.Builder(endpoint).build().initializeRestAdapter().create(apiClass);
  }

  public T provideApiClassWithDateTimeFormat(final Class<T> apiClass, final String dateTimeFormat) {
    return new RetrofitProvider.Builder(endpoint).setDateTimeFormat(dateTimeFormat)
        .build().initializeRestAdapter().create(apiClass);
  }
}
