package com.ceosilvajr.microserviceauth;

import com.ceosilvajr.microserviceauth.config.AppConfig;
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
public class CronFilter implements Filter {

  private static final String CRON_PATH = "cron";
  private static final String API_KEY = "apiKey";

  @Override public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (isAuthorized(httpRequest)) {
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

  private boolean isAuthorized(final HttpServletRequest request) {
    return request.getServletPath().contains(CronFilter.CRON_PATH)
        && AppConfig.SERVICE_API_KEY.getValue().equals(request.getParameter(CronFilter.API_KEY));
  }
}
