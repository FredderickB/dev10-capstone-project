package learn.blindchess.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import learn.blindchess.domain.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GoogleAuthServiceTest {

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private IdTokenVerifier verifier;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private GoogleAuthService googleAuthService;

    @Nested
    class authenticateGoogleUser {

        @Test
        void missingToken() throws DataAccessException {

            Result<String> result = googleAuthService.authenticateGoogleUser("   ");

            assertFalse(result.isSuccess());
            assertEquals(ResultType.INVALID, result.getResultType());
            assertTrue(result.getErrorMessages().contains("Missing idToken parameter"));

        }

        @Test
        void invalidToken() throws GeneralSecurityException, IOException, DataAccessException {

            when(verifier.verify("bad-token")).thenReturn(null);

            Result<String> result = googleAuthService.authenticateGoogleUser("bad-token");

            assertFalse(result.isSuccess());
            assertEquals(ResultType.UNAUTHORIZED, result.getResultType());
            assertTrue(result.getErrorMessages().contains("Invalid Google ID Token"));

            verify(verifier, times(1)).verify("bad-token");
            verifyNoInteractions(userService, jwtProvider);

        }
    }
}