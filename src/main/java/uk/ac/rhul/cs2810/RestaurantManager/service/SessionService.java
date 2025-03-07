package uk.ac.rhul.cs2810.RestaurantManager.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to manage sessions
 */
@Service
public class SessionService {
  private final Map<String, SessionInfo> activeSessions = new ConcurrentHashMap<>();

  private static final long SESSION_TIMEOUT = 43200;

  public String createSession(String username) {
    String sessionId = UUID.randomUUID().toString();
    long expiryTime = Instant.now().getEpochSecond() + SESSION_TIMEOUT;

    SessionInfo sessionInfo = new SessionInfo(username, expiryTime);
    activeSessions.put(sessionId, sessionInfo);

    return sessionId;
  }

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

  public String getUsernameForSession(String sessionId) {
    SessionInfo sessionInfo = activeSessions.get(sessionId);

    if (sessionInfo != null && isSessionValid(sessionId)) {
      return sessionInfo.username;
    }

    return null;
  }

  public void invalidateSession(String sessionId) {
    activeSessions.remove(sessionId);
  }


  private static class SessionInfo {
    private final String username;
    private long expiryTime;

    public SessionInfo(String username, long expiryTime) {
      this.username = username;
      this.expiryTime = expiryTime;
    }
  }
}
