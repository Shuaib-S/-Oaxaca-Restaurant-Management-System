package uk.ac.rhul.cs2810.RestaurantManager.service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * Service for managing user sessions within the application.
 */
@Service
public class SessionService {
  /**
   * A map to store active sessions with their session id as the key.
   */
  private final Map<String, SessionInfo> activeSessions = new ConcurrentHashMap<>();

  /**
   * Session timeout duration in seconds.
   */
  private static final long SESSION_TIMEOUT = 43200;

  /**
   * Default constructor required by spring.
   */
  public SessionService() {}
  /**
   * Creates a new session for a given username.
   *
   * @param username The username for which to create the session.
   * @return The generated session id.
   */
  public String createSession(String username) {
    String sessionId = UUID.randomUUID().toString();
    long expiryTime = Instant.now().getEpochSecond() + SESSION_TIMEOUT;

    SessionInfo sessionInfo = new SessionInfo(username, expiryTime);
    activeSessions.put(sessionId, sessionInfo);

    return sessionId;
  }

  /**
   * Validates a session by checking its existence and expiration.
   *
   * @param sessionId The session ID to validate.
   * @return true if the session is valid.
   */
  public boolean isSessionValid(String sessionId) {
    SessionInfo sessionInfo = activeSessions.get(sessionId);

    if (sessionInfo == null) {
      return false;
    }

    long currentTime = Instant.now().getEpochSecond();

    if (currentTime > sessionInfo.expiryTime) {
      activeSessions.remove(sessionId);
      return false;
    }

    sessionInfo.expiryTime = currentTime + SESSION_TIMEOUT;
    return true;
  }

  /**
   * Retrieves the username associated with a valid session.
   *
   * @param sessionId The session id to look up.
   * @return The username if the session is valid.
   */
  public String getUsernameForSession(String sessionId) {
    SessionInfo sessionInfo = activeSessions.get(sessionId);

    if (sessionInfo != null && isSessionValid(sessionId)) {
      return sessionInfo.username;
    }

    return null;
  }

  /**
   * Invalidates a session, removing it from active sessions.
   *
   * @param sessionId The session id to invalidate.
   */
  public void invalidateSession(String sessionId) {
    activeSessions.remove(sessionId);
  }

  /**
   * Private class to hold session data.
   */
  private static class SessionInfo {
    private final String username;
    private long expiryTime;

    /**
     * Constructs a new SessionInfo object.
     *
     * @param username The username associated with the session.
     * @param expiryTime The time in seconds when the session will expire.
     */
    public SessionInfo(String username, long expiryTime) {
      this.username = username;
      this.expiryTime = expiryTime;
    }
  }
}
