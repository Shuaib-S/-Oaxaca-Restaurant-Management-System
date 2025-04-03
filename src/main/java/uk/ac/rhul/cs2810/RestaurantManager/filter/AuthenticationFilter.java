package uk.ac.rhul.cs2810.RestaurantManager.filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.rhul.cs2810.RestaurantManager.service.SessionService;

/**
 * A filter that makes sure only authenticated users can access protected resources.
 */
@Component
@Order(1)
@WebFilter(urlPatterns = {"/protected/*"})
public class AuthenticationFilter implements Filter {

  private final SessionService sessionService;

  /**
   * Constructs an AuthenticationFilter with the specified {@link SessionService}.
   *
   * @param sessionService the session management service used to validate sessions
   */
  @Autowired
  public AuthenticationFilter(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  /**
   * Filters incoming requests to check authentication status.
   *
   * @param request the servlet request.
   * @param response the servlet response.
   * @param chain the filter chain.
   * @throws IOException if an I/O error occurs during filtering.
   * @throws ServletException if a servlet error occurs during filtering.
   */
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
        requestURI.endsWith(".js") ||
        requestURI.endsWith(".jpg")) {
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

  /**
   * Checks whether a given URI corresponds to a protected resource.
   *
   * @param uri the request URI
   * @return true if the URI starts with "/protected/".
   */
  private boolean isProtectedResource(String uri) {
    return uri.startsWith("/protected/");
  }
}
