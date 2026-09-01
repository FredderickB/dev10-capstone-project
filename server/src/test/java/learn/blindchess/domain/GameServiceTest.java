package learn.blindchess.domain;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.GameRepository;
import learn.blindchess.data.UserRepository;
import learn.blindchess.model.Game;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static learn.blindchess.TestHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameServiceTest {

    @Autowired
    GameService service;

    @MockitoBean
    GameRepository gameRepository;

    @MockitoBean
    UserRepository userRepository;



    @Nested
    class create {

        @Test
        void shouldCreate() throws DataAccessException {

            Game newGame = getNewGame();
            newGame.setGameId(2);

            when(gameRepository.create(any())).thenReturn(newGame);
            when(userRepository.findById(getUserA().getUserId())).thenReturn(getUserA());

            Result<Game> actual = service.create(getUserA().getUserId(),getNewGameRequestDto());
            Result<Game> expected = makeSuccessResult(newGame);

            assertEquals(expected, actual);
        }

        @Test
        void shouldCreateWithNullUser() throws DataAccessException {

            Game newGame = getNewGame();
            newGame.setGameId(2);

            when(gameRepository.create(any())).thenReturn(newGame);
            when(userRepository.findById(anyInt())).thenReturn(null);

            Result<Game> actual = service.create(null, getNewGameRequestDto());
            Result<Game> expected = makeSuccessResult(newGame);

            assertEquals(expected, actual);

        }
    }


}