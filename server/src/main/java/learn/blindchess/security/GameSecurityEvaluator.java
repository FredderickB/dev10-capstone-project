package learn.blindchess.security;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.GameService;
import learn.blindchess.model.Game;
import org.springframework.stereotype.Component;

@Component("gameSecurity")
public class GameSecurityEvaluator {

    private final GameService gameService;

    public GameSecurityEvaluator(GameService gameService) {
        this.gameService = gameService;
    }

    public boolean isAuthorized(UserPrincipal user, int gameId) throws DataAccessException {
        if (user == null) return false;
        Game game = gameService.findById(gameId);
        if (game == null) return true;

        if (user.isGuest()) {
            return user.getGuestIdentifier() != null
                    && user.getGuestIdentifier().equals(game.getGuestId());
        }

        return user.getUserId() != null
                && user.getUserId().equals(game.getUserId());
    }
}
