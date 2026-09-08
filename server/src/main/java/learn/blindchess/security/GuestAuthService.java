package learn.blindchess.security;

import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuestAuthService {

    private final JwtProvider jwtProvider;

    public GuestAuthService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public Result<String> issueGuestToken() {
        Result<String> result = new Result<>();

        try {
            String guestIdentifier = "GUEST_" + UUID.randomUUID();

            String jwt = jwtProvider.generateGuestToken(guestIdentifier);

            if (jwt == null || jwt.isBlank()) {
                result.addErrorMessage("Failed to generate guest authentication token.", ResultType.SERVER_ERROR);
                return result;
            }

            result.setPayload(jwt);

        } catch (Exception e) {
            result.addErrorMessage("An error occurred while creating guest token: " + e.getMessage(), ResultType.SERVER_ERROR);
        }

        return result;
    }
}
