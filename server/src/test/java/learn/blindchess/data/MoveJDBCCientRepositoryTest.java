package learn.blindchess.data;

import learn.blindchess.model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static learn.blindchess.TestHelper.getMoveA;
import static learn.blindchess.TestHelper.getNewMove;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MoveJDBCCientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    MoveRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldNotFind() throws DataAccessException {

        List<Move> expected = List.of();
        List<Move> actual = repository.getMovesByGameId(2);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFind() throws DataAccessException {

        List<Move> expected = List.of(getMoveA());
        List<Move> actual = repository.getMovesByGameId(1);

        assertEquals(expected, actual);

    }

    @Test
    void shouldSaveMove() throws DataAccessException {

        Move expected = getNewMove();
        expected.setMoveId(2);

        Move actual = repository.saveMove(getNewMove());

        assertEquals(expected, actual);

        List<Move> actualMoves = repository.getMovesByGameId(1);
        List<Move> expectedMoves = List.of(getMoveA(), expected);

        assertEquals(expectedMoves, actualMoves);
    }


}