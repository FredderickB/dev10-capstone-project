package learn.blindchess.data;

import learn.blindchess.model.Game;
import learn.blindchess.model.GameStatus;
import learn.blindchess.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRowMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {

        Game game = new Game();

        game.setGameId(rs.getInt("game_id"));
        game.setEngineLevel(rs.getInt("engine_level"));
        game.setFen(rs.getString("fen"));
        game.setStatus(GameStatus.fromStatusId(rs.getInt("status_id")));
        game.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        game.setBoardPeaks(rs.getInt("board_peaks"));

        UserRowMapper userRowMapper = new UserRowMapper();
        User user = userRowMapper.mapRow(rs, rowNum);
        game.setUser(user);

        return game;
    }
}
