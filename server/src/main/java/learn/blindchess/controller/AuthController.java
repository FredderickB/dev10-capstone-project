package learn.blindchess.controller;

import learn.blindchess.security.JwtProvider;
import org.springframework.http.ResponseEntity;
import learn.blindchess.security.GoogleAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final JwtProvider jwtProvider;

    public AuthController(GoogleAuthService googleAuthService, JwtProvider jwtProvider) {
        this.googleAuthService = googleAuthService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/google/login")
    public ResponseEntity<?> authenticateGoogleUser(@RequestBody Map<String, String> requestBody) {
        String idToken = requestBody.get("idToken");
        if (idToken == null || idToken.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing idToken parameter"));
        }

        Payload payload = googleAuthService.verifyToken(idToken);
        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Google ID Token"));
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        String appJwt = jwtProvider.generateToken(email, name);

        return ResponseEntity.ok(Map.of("token", appJwt));
    }
}