package learn.blindchess.data;

import learn.blindchess.model.Game;

public interface GameRepository {

    Game findById(Integer id) throws DataAccessException;
    Game create(Game game) throws DataAccessException;
    boolean update(Game game) throws DataAccessException;

}
