package backend_project.demo.security;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class LoginRateLimiter {
    private static final int MAX_ATTEMPTS = 5;
    private static final long WINDOW_SECONDS = 60;
    private static final long BLOCK_SECONDS = 60;

    private final ConcurrentHashMap<String, Deque<Instant>> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String username) {
        Deque<Instant> userAttempts = attempts.get(username);
        if (userAttempts == null) return false;

        synchronized (userAttempts) {
            Instant now = Instant.now();
            Instant windowStart = now.minusSeconds(WINDOW_SECONDS);
            Instant blockStart = now.minusSeconds(BLOCK_SECONDS);

            while (!userAttempts.isEmpty() && userAttempts.peekFirst().isBefore(windowStart)) {
                userAttempts.pollFirst();
            }

            if (userAttempts.size() >= MAX_ATTEMPTS) {
                if (userAttempts.peekLast().isAfter(blockStart)) {
                    return true; 
                } else {
                    userAttempts.clear(); 
                }
            }
            return false;
        }
    }

    public void recordFailure(String username) {
        Deque<Instant> userAttempts = attempts.computeIfAbsent(username, k -> new ArrayDeque<>());
        synchronized (userAttempts) {
            userAttempts.addLast(Instant.now());
        }
    }

    public void reset(String username) {
        attempts.remove(username); 
    }
}
