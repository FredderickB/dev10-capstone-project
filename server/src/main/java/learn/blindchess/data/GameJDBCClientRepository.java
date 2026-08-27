package learn.blindchess.data;

import learn.blindchess.model.Game;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GameJDBCClientRepository implements GameRepository{

    private final JdbcClient client;

    @Override
    public Game findById(Integer id) throws DataAccessException {
        String sql = """
                select
                    g.game_id,
                    g.user_id,
                    g.engine_level,
                    g.fen,
                    g.status_id,
                    g.board_peaks,
                    g.created_at,
                    u.username,
                    u.email
                from game g
                left join user u on u.user_id = g.user_id
                where game_id = ?;
                """;

        return client.sql(sql)
                .param(id)
                .query(new GameRowMapper())
                .optional().orElse(null);
    }

    public GameJDBCClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Game create(Game game) throws DataAccessException {
        String sql = """
                 insert into game (user_id, engine_level, fen, status_id, created_at, board_peaks) values
                    	(:userId, :engineLevel, :fen, :statusId, :createdAt, :boardPeaks);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("userId", game.getUser().getUserId())
                .param("engineLevel", game.getEngineLevel())
                .param("fen", game.getFen())
                .param("statusId", game.getStatus().getStatusId())
                .param("createdAt", game.getCreatedAt())
                .param("boardPeaks", game.getBoardPeaks())
                .update(keyHolder, "game_id");

        if (rowsAffected == 0) {
            return null;
        }

        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }
}
