package learn.blindchess.data;

import learn.blindchess.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Objects;

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
    void shouldFindAll() throws DataAccessException {

        List<Game> actual = repository.findAllByUserId(1);
        List<Game> expected = List.of(getGameA());

        assertEquals(expected, actual);

    }

    @Test
    void shouldNotFindAny() throws DataAccessException {

        List<Game> actual = repository.findAllByUserId(-999);
        List<Game> expected = List.of();

        assertEquals(expected, actual);

    }

    @Test
    void shouldCreate() throws DataAccessException {

        Game expected = getNewGame();
        expected.setGameId(2);
        Game actual = repository.create(getNewGame());

        assertEquals(expected, actual);

    }

    @Test
    void shouldCreateWithNullUser() throws DataAccessException {

        Game newGame = getNewGame();
        newGame.setUserId(null);

        Game expected = newGame;
        expected.setGameId(2);
        Game actual = repository.create(newGame);

        assertEquals(expected, actual);

    }

    @Test
    void shouldUpdate() throws DataAccessException {

        Game updatedGame = getGameA();
        updatedGame.setFen("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        boolean actual = repository.update(updatedGame);

        assertTrue(actual);
        assertEquals(updatedGame, repository.findById(updatedGame.getGameId()));

    }

    @Test
    void shouldNotUpdate() throws DataAccessException {

        Game updatedGame = getGameA();
        updatedGame.setFen("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        updatedGame.setGameId(9999);

        boolean actual = repository.update(updatedGame);

        assertFalse(actual);
    }

    @Test
    void shouldSoftDelete() throws DataAccessException {

        boolean actual = repository.delete(1);

        assertTrue(actual);

        Game gameFound = repository.findById(1);
        Game expected = getGameA();
        expected.setDeleted(true);

        assertEquals(expected, gameFound);
    }


}