package uk.ac.rhul.cs2810.RestaurantManager.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import uk.ac.rhul.cs2810.RestaurantManager.service.SessionService;

import java.io.IOException;

/**
 * Filter that will protect protected tr
 */
@Component
@Order(1)
@WebFilter(urlPatterns = {"/protected/*"})
public class AuthenticationFilter implements Filter {

  private final SessionService sessionService;

  @Autowired
  public AuthenticationFilter(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String requestURI = httpRequest.getRequestURI();

    if (requestURI.endsWith("/waiters-login.html") ||
        requestURI.contains("/api/login") ||
        requestURI.endsWith("/menu.html") ||
        requestURI.endsWith(".css") ||
        requestURI.endsWith(".js")) {
      chain.doFilter(request, response);
      return;
    }

    if (isProtectedResource(requestURI)) {
      String sessionId = null;
      Cookie[] cookies = httpRequest.getCookies();

      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("RESTAURANT_SESSION".equals(cookie.getName())) {
            sessionId = cookie.getValue();
            break;
          }
        }
      }

      if (sessionId != null && sessionService.isSessionValid(sessionId)) {
        chain.doFilter(request, response);
      } else {
        httpResponse.sendRedirect("/waiters-login.html");
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  private boolean isProtectedResource(String uri) {
    return uri.startsWith("/protected/");
  }
}
