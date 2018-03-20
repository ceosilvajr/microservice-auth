package com.ceosilvajr.microserviceauth.jwt;

import com.ceosilvajr.microserviceauth.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.Charset;
import javax.xml.bind.DatatypeConverter;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public final class PayloadDecoder {

  private final String encodedPayload;

  public static PayloadDecoder instanceOf(final String encodedToken) {
    return new PayloadDecoder(encodedToken);
  }

  private PayloadDecoder(final String encodedPayload) {
    this.encodedPayload = encodedPayload;
  }

  public Payload decode() {
    final Claims claims = getClaims(encodedPayload);
    return new Payload.Builder(
        new String(DatatypeConverter.parseBase64Binary(getField(claims, "appId")), Charset.defaultCharset()),
        new String(DatatypeConverter.parseBase64Binary(getField(claims, "appKey")), Charset.defaultCharset())
    ).build();
  }

  private Claims getClaims(final String encodedToken) {
    return Jwts.parser().setSigningKey(AppConfig.SERVICE_SECRET_KEY.getValue()
        .getBytes(Charset.defaultCharset()))
        .parseClaimsJws(encodedToken).getBody();
  }

  private String getField(final Claims claims, final String field) {
    return (String) claims.get(field);
  }
}
