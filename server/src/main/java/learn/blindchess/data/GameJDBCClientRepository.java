package learn.blindchess.data;

import learn.blindchess.model.Game;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class GameJDBCClientRepository implements GameRepository{

    private final JdbcClient client;

    @Override
    public Game findById(Integer id) throws DataAccessException {
        String sql = """
                select
                    g.game_id,
                    g.user_id,
                    g.guest_id,
                    g.engine_level,
                    g.fen,
                    g.status_id,
                    g.player_color_id,
                    g.board_peaks,
                    g.created_at,
                    g.is_deleted,
                    u.username,
                    u.email
                from game g
                left join user u on u.user_id = g.user_id
                join player_color p on p.player_color_id = g.player_color_id
                where game_id = ?;
                """;

        return client.sql(sql)
                .param(id)
                .query(new GameRowMapper())
                .optional().orElse(null);
    }

    @Override
    public List<Game> findAllByUserId(int userId) throws DataAccessException {
        String sql = """
                select
                    g.game_id,
                    g.user_id,
                    g.guest_id,
                    g.engine_level,
                    g.fen,
                    g.status_id,
                    g.player_color_id,
                    g.board_peaks,
                    g.is_deleted,
                    g.created_at,
                    u.username,
                    u.email
                from game g
                left join user u on u.user_id = g.user_id
                join player_color p on p.player_color_id = g.player_color_id
                where g.user_id = ? and g.is_deleted = 0;
                """;

        return client.sql(sql)
                .param(userId)
                .query(new GameRowMapper())
                .list();
    }

    public GameJDBCClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Game create(Game game) throws DataAccessException {
        String sql = """
                 insert into game (user_id, guest_id, engine_level, fen, status_id, created_at, board_peaks, player_color_id) values
                    	(:userId, :guestId, :engineLevel, :fen, :statusId, :createdAt, :boardPeaks, :playerColorId);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("userId", game.getUserId(), Types.INTEGER)
                .param("guestId", game.getGuestId())
                .param("engineLevel", game.getEngineLevel())
                .param("fen", game.getFen())
                .param("statusId", game.getStatus().getStatusId())
                .param("createdAt", game.getCreatedAt())
                .param("boardPeaks", game.getBoardPeaks())
                .param("playerColorId", game.getPlayerColor().getPlayerColorId())
                .update(keyHolder, "game_id");

        if (rowsAffected == 0) {
            return null;
        }

        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public boolean update(Game game) throws DataAccessException {
        String sql = """
            UPDATE game SET
                fen = :fen,
                status_id = :statusId,
                board_peaks = :boardPeaks
            WHERE game_id = :gameId;
            """;

        int rowsAffected = client.sql(sql)
                .param("fen", game.getFen())
                .param("gameId", game.getGameId())
                .param("statusId", game.getStatus().getStatusId())
                .param("boardPeaks", game.getBoardPeaks())
                .update();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(int gameId) throws DataAccessException {
        String sql = """
            UPDATE game SET
                is_deleted = 1
            WHERE game_id = :gameId;
            """;

        int rowsAffected = client.sql(sql)
                .param("gameId", gameId)
                .update();

        return rowsAffected > 0;
    }
}
