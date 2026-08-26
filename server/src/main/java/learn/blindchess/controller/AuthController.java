package learn.blindchess.controller;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.Result;
import learn.blindchess.domain.UserService;
import learn.blindchess.model.User;
import learn.blindchess.security.JwtProvider;
import org.springframework.http.ResponseEntity;
import learn.blindchess.security.GoogleAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static learn.blindchess.controller.ErrorResponse.build;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public AuthController(GoogleAuthService googleAuthService, JwtProvider jwtProvider, UserService userService) {
        this.googleAuthService = googleAuthService;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/google/login")
    public ResponseEntity<?> authenticateGoogleUser(@RequestBody Map<String, String> requestBody) throws DataAccessException {
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

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);

        Result<User> result = userService.processGoogleUser(user);

        if (!result.isSuccess()) {
            return build(result);
        } else {

            String appJwt = jwtProvider.generateToken(email, name);
            return ResponseEntity.ok(Map.of("token", appJwt));

        }

    }
}