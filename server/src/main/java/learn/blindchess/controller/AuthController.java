package learn.blindchess.controller;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.Result;
import learn.blindchess.dto.AuthResponse;
import learn.blindchess.dto.GoogleLoginRequest;
import learn.blindchess.security.GuestAuthService;
import org.springframework.http.ResponseEntity;
import learn.blindchess.security.GoogleAuthService;
import org.springframework.web.bind.annotation.*;

import static learn.blindchess.controller.ErrorResponse.build;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final GuestAuthService guestAuthService;

    public AuthController(GoogleAuthService googleAuthService, GuestAuthService guestAuthService) {
        this.googleAuthService = googleAuthService;
        this.guestAuthService = guestAuthService;
    }

    @PostMapping("/google/login")
    public ResponseEntity<?> authenticateGoogleUser(@RequestBody GoogleLoginRequest request) throws DataAccessException {
        Result<String> result = googleAuthService.authenticateGoogleUser(request.idToken());

        if (!result.isSuccess()) {
            return build(result);
        }

        return ResponseEntity.ok(new AuthResponse(result.getPayload()));
    }

    @PostMapping("/guest")
    public ResponseEntity<?> authenticateGuestUser() throws DataAccessException {
        Result<String> result = guestAuthService.issueGuestToken();

        if (!result.isSuccess()) {
            return build(result);
        }

        return ResponseEntity.ok(new AuthResponse(result.getPayload()));
    }
}