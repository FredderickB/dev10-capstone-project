package learn.blindchess.data;

import learn.blindchess.model.Move;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoveRowMapper implements RowMapper<Move> {

    @Override
    public Move mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Move(
                rs.getInt("move_id"),
                rs.getInt("game_id"),
                rs.getInt("move_number"),
                rs.getString("move_san"),
                rs.getString("fen_after")
        );
    }
}
