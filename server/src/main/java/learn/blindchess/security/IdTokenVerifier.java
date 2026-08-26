package learn.blindchess.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public interface IdTokenVerifier {
    GoogleIdToken.Payload verify(String idTokenString);
}
