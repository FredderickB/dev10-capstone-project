package learn.blindchess.security;

public interface JwtProvider {

    String generateToken(String email, String username);

    boolean validateToken(String token);

    String getEmailFromToken(String token);

}
