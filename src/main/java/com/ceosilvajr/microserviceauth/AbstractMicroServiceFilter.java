package com.ceosilvajr.microserviceauth;

import com.ceosilvajr.microserviceauth.config.MicroServiceConfig;
import com.ceosilvajr.microserviceauth.config.MicroServiceConstants;
import com.ceosilvajr.microserviceauth.jwt.Payload;
import com.ceosilvajr.microserviceauth.jwt.PayloadDecoder;
import com.ceosilvajr.microserviceauth.jwt.Platform;
import com.ceosilvajr.servletutil.HttpResponseCodes;
import com.ceosilvajr.servletutil.ServletResponseUtility;
import com.ceosilvajr.servletutil.dto.ErrorResponse;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created date 20/03/2018
 *
 * @author ceosilvajr@gmail.com
 **/
public abstract class AbstractMicroServiceFilter implements Filter {

  @Override public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (isAuthorized(httpRequest)) {
      chain.doFilter(httpRequest, httpResponse);
    } else {
      ServletResponseUtility.instanceOf(httpResponse, new ErrorResponse.Builder(HttpResponseCodes.RC_UNAUTHORIZED,
          MicroServiceConstants.UNAUTHORIZED_ERROR_MESSAGE).build()).toJson();
    }
  }

  public abstract boolean isAuthorized(final HttpServletRequest httpRequest);

  public boolean isAuthorizedService(final HttpServletRequest httpRequest) {
    final String token = httpRequest.getHeader(MicroServiceConfig.SERVICE_AUTH_HEADER_NAME.getValue());
    try {
      final Payload payload = PayloadDecoder.instanceOf(token).decode();
      final String appId = MicroServiceConfig.SERVICE_APPID.getValue();
      final String appKey = MicroServiceConfig.SERVICE_APPKEY.getValue();
      return payload.getPlatform().equals(Platform.SERVICE)
          && appId.equals(payload.getAppId()) && appKey.equals(payload.getAppKey());
    } catch (final IllegalArgumentException | JwtException e) {
      return false;
    }
  }

  public boolean isAuthorizedCron(final HttpServletRequest httpRequest) {
    final String token = httpRequest.getParameter(MicroServiceConfig.SERVICE_AUTH_REQUEST_NAME.getValue());
    return MicroServiceConfig.SERVICE_APIKEY.getValue().equals(token);
  }
}
