package backend_project.demo.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import backend_project.demo.dto.UserRequest;
import backend_project.demo.model.User;
import backend_project.demo.security.JwtService;
import backend_project.demo.security.LoginRateLimiter;
import backend_project.demo.service.UserService;
import jakarta.validation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final LoginRateLimiter rateLimiter;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserService userService, LoginRateLimiter rateLimiter) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.rateLimiter = rateLimiter;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody UserRequest request) {
        String username = request.username();
        if (rateLimiter.isBlocked(username)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "too many attempts, try again later");
        }
        
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, request.password())
            );
            rateLimiter.reset(username);

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            String token = jwtService.generate(userDetails.getUsername(), role);
            return Map.of("access_token", token);

        } catch (Exception e) {
            rateLimiter.recordFailure(username);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "bad credentials");
        }
    }

    @GetMapping("/me")
    public User me(Authentication auth) {
        if (auth == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return userService.findByUsername(auth.getName());
    }
}