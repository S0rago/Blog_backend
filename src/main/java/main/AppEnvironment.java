package main;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppEnvironment {
    private final Map<String, Integer> sessions;

    public AppEnvironment() {
        this.sessions = new HashMap<>();
    }

    public Map<String, Integer> getSessions() {
        return sessions;
    }

    public void addSession(String session, Integer userId) {
        sessions.put(session, userId);
    }

    public Integer getUserIdFromSession(String session) {
        return sessions.getOrDefault(session, null);
    }

    public void removeSession(String session) {
        sessions.remove(session);
    }

    //TODO Add captcha settings
}
