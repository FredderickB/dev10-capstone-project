package learn.blindchess.security;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import learn.blindchess.domain.UserService;
import learn.blindchess.model.User;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

@Service
public class GoogleAuthService {
    private final IdTokenVerifier verifier;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public GoogleAuthService(IdTokenVerifier verifier, JwtProvider jwtProvider, UserService userService) {
        this.verifier = verifier;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public Result<String> authenticateGoogleUser(String idToken) throws DataAccessException {
        Result<String> result = new Result<>();

        if (idToken == null || idToken.isBlank()) {
            result.addErrorMessage("Missing idToken parameter", ResultType.INVALID);
            return result;
        }

        GoogleIdToken.Payload payload = verifier.verify(idToken);
        if (payload == null) {
            result.addErrorMessage("Invalid Google ID Token", ResultType.UNAUTHORIZED);
            return result;
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);

        Result<User> userResult = userService.processGoogleUser(user);
        if (!userResult.isSuccess()) {
            for (String err : userResult.getErrorMessages()) {
                result.addErrorMessage(err, userResult.getResultType());
            }
            return result;
        }

        String appJwt = jwtProvider.generateToken(email, name);
        result.setPayload(appJwt);
        return result;
    }
}
