package com.ceosilvajr.microserviceauth;

import com.ceosilvajr.microserviceauth.config.AppConfig;
import com.ceosilvajr.microserviceauth.jwt.Payload;
import com.ceosilvajr.microserviceauth.jwt.PayloadDecoder;
import com.ceosilvajr.microserviceauth.jwt.Platform;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
public class MicroServiceFilter implements Filter {

  @Override public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    final String encodedToken = httpRequest.getHeader(AppConfig.SERVICE_HEADER_NAME.getValue());
    if (isAuthorized(encodedToken)) {
      chain.doFilter(httpRequest, httpResponse);
    } else {
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  @Override public void init(final FilterConfig filterConfig) throws ServletException {
    // Intended to be empty
  }

  @Override public void destroy() {
    // Intended to be empty
  }

  private boolean isAuthorized(final String token) {
    final Payload payload = PayloadDecoder.instanceOf(token).decode();
    final String appId = AppConfig.SERVICE_APP_ID.getValue();
    final String appKey = AppConfig.SERVICE_APP_KEY.getValue();
    return payload.getPlatform().equals(Platform.SERVICE)
        && appId.equals(payload.getAppId()) && appKey.equals(payload.getAppKey());
  }
}
