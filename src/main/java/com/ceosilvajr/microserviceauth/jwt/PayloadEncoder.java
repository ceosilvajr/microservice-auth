package com.ceosilvajr.microserviceauth.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public final class PayloadEncoder {

  private final String secretKey;

  private PayloadEncoder(final String secretKey) {
    this.secretKey = secretKey;
  }

  public static PayloadEncoder instanceOf(final String secretKey) {
    return new PayloadEncoder(secretKey);
  }

  public String encode(final Payload payload) {
    return Jwts.builder().setPayload(new Gson().toJson(encrypted(payload), Payload.class))
        .signWith(SignatureAlgorithm.HS256, convertToBase64(secretKey)).compact();
  }

  private Payload encrypted(final Payload payload) {
    return new Payload.Builder(convertToBase64(payload.getAppId()), convertToBase64(payload.getAppKey())).build();
  }

  private String convertToBase64(final String string) {
    final byte[] bytes = Base64.encodeBase64(string.getBytes(Charset.defaultCharset()));
    return new String(bytes, Charset.defaultCharset());
  }
}
