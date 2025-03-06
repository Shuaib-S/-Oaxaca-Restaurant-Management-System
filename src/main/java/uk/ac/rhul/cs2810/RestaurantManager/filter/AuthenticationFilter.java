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
    if (requestURI.contains("/api/login")) {
      chain.doFilter(request, response);
    }

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
      httpResponse.sendRedirect("/login.html");
    }
  }

  private boolean isProtectedResource(String uri) {
    return uri.startsWith("/protected/");
  }
}
