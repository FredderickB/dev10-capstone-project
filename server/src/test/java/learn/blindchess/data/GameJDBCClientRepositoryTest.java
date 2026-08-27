package learn.blindchess.data;

import learn.blindchess.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;
import static learn.blindchess.TestHelper.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameJDBCClientRepositoryTest {

    @Autowired
    private JdbcClient client;

    @Autowired
    private GameRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldFindById() throws DataAccessException {

        Game actual = repository.findById(1);
        Game expected = getGameA();

        assertEquals(expected, actual);
    }

    @Test
    void shouldCreate() throws DataAccessException {

        Game expected = getNewGame();
        expected.setGameId(2);
        Game actual = repository.create(getNewGame());

        assertEquals(expected, actual);

    }
}