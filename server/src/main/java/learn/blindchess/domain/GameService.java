package learn.blindchess.domain;

import com.github.bhlangonijr.chesslib.Board;
import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.GameRepository;
import learn.blindchess.data.UserRepository;
import learn.blindchess.dto.GameRequestDto;
import learn.blindchess.model.Game;
import learn.blindchess.model.GameStatus;
import learn.blindchess.model.PlayerColor;
import learn.blindchess.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Game findById(int gameId) throws DataAccessException {

        return gameRepository.findById(gameId);
    }

    public Result<Game> create(Integer userId, GameRequestDto gameRequestDto) throws DataAccessException {

        Result<Game> result = validateGameRequest(gameRequestDto);

        if (result.isSuccess()) {
            Game game = makeNewGame(userId, gameRequestDto);
            game = gameRepository.create(game);
            result.setPayload(game);
        }

        return result;

    }

    public boolean update(Game game) throws DataAccessException {

        return gameRepository.update(game);

    }

    private Result<Game> validateGameRequest(GameRequestDto dto) {

        Result<Game> result = new Result<>();

        if (dto == null) {
            result.addErrorMessage("Game request payload null", ResultType.INVALID);
            return result;
        }

        return result;
    }

    private Game makeNewGame(Integer userId, GameRequestDto dto) throws DataAccessException {

        User user = userRepository.findById(userId);
        if (user == null) {
            userId = null;
        }
        Board board = new Board();
        Game game = new Game();


        game.setUserId(userId);
        game.setPlayerColor(PlayerColor.valueOf(dto.playerColor()));
        game.setBoardPeaks(0);
        game.setCreatedAt(LocalDateTime.now());
        game.setFen(board.getFen());
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setEngineLevel(dto.engineLevel());

        return game;
    }
}
