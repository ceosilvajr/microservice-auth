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
import java.util.logging.Logger;
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

  private static final Logger LOGGER = Logger.getLogger(MicroServiceFilter.class.getName());

  @Override public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    final String encodedToken = httpRequest.getHeader(MicroServiceConfig.SERVICE_HEADER_NAME.getValue());
    if (isAuthorized(encodedToken)) {
      chain.doFilter(httpRequest, httpResponse);
    } else {
      ServletResponseUtility.instanceOf(httpResponse, new ErrorResponse.Builder(HttpResponseCodes.RC_UNAUTHORIZED,
          MicroServiceConstants.UNAUTHORIZED_ERROR_MESSAGE).build()).toJson();
    }
  }

  @Override public void init(final FilterConfig filterConfig) throws ServletException {
    // Intended to be empty
  }

  @Override public void destroy() {
    // Intended to be empty
  }

  private boolean isAuthorized(final String token) {
    try {
      final Payload payload = PayloadDecoder.instanceOf(token).decode();
      LOGGER.info(payload.toString());
      final String appId = MicroServiceConfig.SERVICE_APP_ID.getValue();
      final String appKey = MicroServiceConfig.SERVICE_APP_KEY.getValue();
      return payload.getPlatform().equals(Platform.SERVICE)
          && appId.equals(payload.getAppId()) && appKey.equals(payload.getAppKey());
    } catch (final IllegalArgumentException | JwtException e) {
      LOGGER.warning(e.getMessage());
      return false;
    }
  }
}
