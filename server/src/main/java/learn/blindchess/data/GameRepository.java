package learn.blindchess.data;

import learn.blindchess.model.Game;

import java.util.List;

public interface GameRepository {

    Game findById(Integer id) throws DataAccessException;
    Game create(Game game) throws DataAccessException;
    boolean update(Game game) throws DataAccessException;
    List<Game> findAllByUserId(int userId) throws DataAccessException;

}
