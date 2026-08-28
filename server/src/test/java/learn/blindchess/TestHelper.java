package learn.blindchess;

import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import learn.blindchess.dto.GameRequestDto;
import learn.blindchess.model.*;

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

    private static final Move MOVE_A = new Move(1, 1, 1, "E4", "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
    private static final Move NEW_MOVE = new Move(null, 1, 1, "E5", "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2");

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

    public static Move getMoveA() { return new Move(MOVE_A);}
    public static Move getNewMove() { return new Move(NEW_MOVE); }

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
