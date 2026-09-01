package learn.blindchess.security;

import learn.blindchess.model.User;

public interface JwtProvider {

    String generateToken(User user);

    boolean validateToken(String token);

    Integer getIntFromToken(String token);

}
