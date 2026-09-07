package learn.blindchess.data;

import learn.blindchess.model.Game;
import learn.blindchess.model.GameStatus;
import learn.blindchess.model.PlayerColor;
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
        game.setPlayerColor(PlayerColor.fromPlayerColorId(rs.getInt("player_color_id")));
        game.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        game.setBoardPeaks(rs.getInt("board_peaks"));
        game.setUserId(rs.getInt("user_id"));
        game.setDeleted(rs.getBoolean("is_deleted"));
        game.setGuestId(rs.getString("guest_id"));


        return game;
    }
}
