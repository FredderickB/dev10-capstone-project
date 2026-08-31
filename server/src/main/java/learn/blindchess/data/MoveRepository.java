package learn.blindchess.data;

import learn.blindchess.model.Move;

import java.util.List;

public interface MoveRepository {

    Move saveMove(Move move) throws DataAccessException;

    List<Move> getMovesByGameId(int gameId) throws DataAccessException;
}
