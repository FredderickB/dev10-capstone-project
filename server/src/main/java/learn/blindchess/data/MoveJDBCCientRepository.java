package learn.blindchess.data;

import learn.blindchess.model.Move;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MoveJDBCCientRepository implements MoveRepository{

    private JdbcClient client;

    public MoveJDBCCientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Move saveMove(Move move) throws DataAccessException {

        String sql = """
                insert into move (game_id, move_number, move_san, fen_after) values
                		(:gameId, :moveNumber, :moveSan, :fenAfter);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("gameId", move.getGameId())
                .param("moveNumber", move.getMoveNumber())
                .param("moveSan", move.getMoveSan())
                .param("fenAfter", move.getFenAfter())
                .update(keyHolder, "move_id");

        if (rowsAffected == 0) {
            return null;
        }

        move.setMoveId(keyHolder.getKey().intValue());

        return move;

    }

    @Override
    public List<Move> getMovesByGameId(int gameId) throws DataAccessException {

        String sql = """
                select
                    move_id,
                    m.game_id,
                    move_number,
                    move_san,
                    fen_after
                from move m
                join game g on g.game_id = m.game_id
                where m.game_id = ?;
                """;

        return client.sql(sql)
                .param(gameId)
                .query(new MoveRowMapper())
                .list();
    }
}
