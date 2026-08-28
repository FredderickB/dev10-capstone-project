package learn.blindchess;

import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import learn.blindchess.dto.GameRequestDto;
import learn.blindchess.model.Game;
import learn.blindchess.model.GameStatus;
import learn.blindchess.model.PlayerColor;
import learn.blindchess.model.User;

import java.time.LocalDateTime;

public class TestHelper {

    private static final User USER_A = new User(1, "a", "a@a.com");
    private static final User USER_B = new User(2, "b", "b@b.com");
    private static final User NEW_USER = new User(null, "c", "c@c.com");

    public static final LocalDateTime DATE_A = LocalDateTime.of(2000, 1, 1, 1, 1);

    private static final Game GAME_A = new Game(
            1,
            1000,
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            DATE_A,
            GameStatus.IN_PROGRESS,
            PlayerColor.WHITE,
            0,
            1);
    private static final Game NEW_GAME = new Game(
            null,
            500,
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            DATE_A,
            GameStatus.IN_PROGRESS,
            PlayerColor.WHITE,
            0,
            1);

    private static final GameRequestDto GAME_REQUEST_DTO_A = new GameRequestDto("WHITE", 1000);

    public static User getUserA() {
        return new User(USER_A);
    }
    public static User getUserB() {
        return new User(USER_B);
    }
    public static User getNewUser() {
        return new User(NEW_USER);
    }

    public static Game getGameA() { return new Game(GAME_A);}
    public static Game getNewGame() { return new Game(NEW_GAME);}

    public static GameRequestDto getNewGameRequestDto() { return new GameRequestDto(GAME_REQUEST_DTO_A.playerColor(), GAME_REQUEST_DTO_A.engineLevel());}

    public static <T> Result<T> makeSuccessResult(T payload) {

        Result<T> result = new Result<>();
        result.setPayload(payload);
        return result;

    }

    public static <T> Result<T> makeInvalidResult(String message) {

        Result<T> result = new Result<>();
        result.addErrorMessage(message, ResultType.INVALID);
        return result;

    }

    public static <T> Result<T> makeNotFoundResult(String message) {

        Result<T> result = new Result<>();
        result.addErrorMessage(message, ResultType.NOT_FOUND);
        return result;

    }

}
