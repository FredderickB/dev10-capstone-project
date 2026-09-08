package learn.blindchess.domain;

import com.github.bhlangonijr.chesslib.Board;
import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.GameRepository;
import learn.blindchess.data.UserRepository;
import learn.blindchess.dto.GameRequestDto;
import learn.blindchess.dto.GameSummaryDto;
import learn.blindchess.model.Game;
import learn.blindchess.model.GameStatus;
import learn.blindchess.model.PlayerColor;
import learn.blindchess.model.User;
import learn.blindchess.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static learn.blindchess.model.PlayerColor.BLACK;
import static learn.blindchess.model.PlayerColor.WHITE;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ChessLibService chessLibService;
    private final MoveService moveService;

    public GameService(GameRepository gameRepository, UserRepository userRepository, ChessLibService chessLibService, MoveService moveService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.chessLibService = chessLibService;
        this.moveService = moveService;
    }

    public Game findById(int gameId) throws DataAccessException {

        return gameRepository.findById(gameId);
    }

    public List<GameSummaryDto> findAllByUserId(int userId) throws DataAccessException {
        List<Game> gameList = gameRepository.findAllByUserId(userId);
        List<GameSummaryDto> gameSummaryDtoList = new ArrayList<>();

        for (Game game : gameList) {
            int moveCount = chessLibService.getMoveNumber(game.getFen());
            gameSummaryDtoList.add(new GameSummaryDto(game, moveCount));
        }

        return gameSummaryDtoList;
    }

    public Result<Game> create(UserPrincipal user, GameRequestDto gameRequestDto) throws DataAccessException {

        Result<Game> result = validateGameRequest(gameRequestDto);

        if (!result.isSuccess()) {
            return result;
        }

        Game game = makeNewGame(user, gameRequestDto);
        game = gameRepository.create(game);

        if (game.getPlayerColor() == BLACK) {
            MoveService.EngineTurnResult engineResult = moveService.runStockfishTurn(game.getFen(), game.getEngineLevel(), 1, game.getGameId());
            game.setFen(engineResult.finalFen());
            update(game);
        }
        result.setPayload(game);

        return result;
    }

    public boolean resign(Game game) throws DataAccessException {

        PlayerColor playerColor = game.getPlayerColor();
        PlayerColor engineColor = playerColor == WHITE ? BLACK : WHITE;

        if (engineColor == WHITE) {
            game.setStatus(GameStatus.WHITE_WIN);
        } else {
            game.setStatus(GameStatus.BLACK_WIN);
        }

        return update(game);

    }

    public boolean update(Game game) throws DataAccessException {

        return gameRepository.update(game);

    }

    public boolean delete(int gameId) throws DataAccessException {

        return gameRepository.delete(gameId);

    }

    private Result<Game> validateGameRequest(GameRequestDto dto) {

        Result<Game> result = new Result<>();

        if (dto == null) {
            result.addErrorMessage("Game request payload null", ResultType.INVALID);
            return result;
        }

        return result;
    }

    private Game makeNewGame(UserPrincipal user, GameRequestDto dto) throws DataAccessException {

        if (user == null) {
            return null;
        }

        Game game = new Game();
        Board board = new Board();

        if (user.isGuest()) {
            game.setUserId(null);
            game.setGuestId(user.getGuestIdentifier());
        } else {
            game.setUserId(user.getUserId());
            game.setGuestId(null);
        }

        game.setPlayerColor(PlayerColor.valueOf(dto.playerColor()));
        game.setBoardPeaks(0);
        game.setCreatedAt(LocalDateTime.now());
        game.setFen(board.getFen());
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setEngineLevel(dto.engineLevel());

        return game;
    }
}
