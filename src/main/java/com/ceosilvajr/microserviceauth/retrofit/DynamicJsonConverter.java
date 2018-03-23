/*
 * Copyright (c) 2018. ceosilvajr All rights reserved
 */

package com.ceosilvajr.microserviceauth.retrofit;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;

/**
 * Created by ceosilvajr on 12/7/16.
 *
 * @author ceosilvajr@gmail.com
 */
public final class DynamicJsonConverter extends GsonConverter {

  private final Gson gson;

  public DynamicJsonConverter(final Gson gson) {
    super(gson);
    this.gson = gson;
  }

  @Override public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
    try {
      final InputStream in = body.in();
      final String string = fromStream(in);
      in.close();
      if (String.class.equals(type)) {
        return string;
      } else {
        return gson.fromJson(string, type);
      }
    } catch (final IOException e) {
      return super.fromBody(body, type);
    }
  }

  @SuppressWarnings("PMD")
  private static String fromStream(final InputStream inputStream) throws IOException {
    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    final StringBuilder out = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      out.append(line);
      out.append("\r\n");
    }
    return out.toString();
  }
}