package learn.blindchess.domain;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.GameRepository;
import learn.blindchess.data.MoveRepository;
import learn.blindchess.dto.FullTurnDto;
import learn.blindchess.dto.MoveDto;
import learn.blindchess.dto.MoveRequestDto;
import learn.blindchess.model.Game;
import learn.blindchess.model.GameStatus;
import learn.blindchess.model.Move;
import learn.blindchess.model.PlayerColor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoveService {

    private final MoveRepository moveRepository;
    private final GameRepository gameRepository;
    private final StockFishService stockFishService;
    private final ChessLibService chessLibService;

    public MoveService(MoveRepository moveRepository,
                       GameRepository gameRepository,
                       StockFishService stockFishService,
                       ChessLibService chessLibService) {
        this.moveRepository = moveRepository;
        this.gameRepository = gameRepository;
        this.stockFishService = stockFishService;
        this.chessLibService = chessLibService;
    }

    public Result<FullTurnDto> processPlayerMove(MoveRequestDto request) throws DataAccessException {

        Result<FullTurnDto> result = new Result<>();

        Game game = gameRepository.findById(request.gameId());
        if (game == null) {
            result.addErrorMessage("Game id " + request.gameId() + " not found", ResultType.NOT_FOUND);
        }

        if (!chessLibService.isLegalMove(request.playerSan(), request.currentFen())) {
            return buildIllegalMoveResult(request);
        }

        PlayerTurnResult playerResult = executePlayerTurn(request, game.getGameId());

        EngineTurnResult engineResult = resolveEngineTurn(playerResult, request, game);

        updateGameRecord(game, engineResult.finalFen(), request.peakedAtBoard());

        FullTurnDto fullTurnDto = buildTurnResponse(request, playerResult, engineResult);
        result.setPayload(fullTurnDto);
        return result;
    }


    private PlayerTurnResult executePlayerTurn(MoveRequestDto request, int gameId) throws DataAccessException {
        String fen = chessLibService.getUpdatedFen(request.currentFen(), request.playerSan());
        GameStatus status = chessLibService.getGameStatus(fen);

        saveMove(fen, gameId, request.moveNumber(), request.playerSan());

        return new PlayerTurnResult(fen, status);
    }

    private EngineTurnResult resolveEngineTurn(PlayerTurnResult playerResult, MoveRequestDto request, Game game)
            throws DataAccessException {

        if (playerResult.status() != GameStatus.IN_PROGRESS) {
            game.setStatus(playerResult.status());
            return new EngineTurnResult(
                    playerResult.fen(),
                    null,
                    formatGameEndMessage(playerResult.status())
            );
        }

        EngineTurnResult engineResult = runStockfishTurn(
                playerResult.fen(),
                request.engineLevel(),
                request.moveNumber() + 1,
                game.getGameId()
        );

        if (engineResult.status() != GameStatus.IN_PROGRESS) {
            game.setStatus(engineResult.status());
        }

        return engineResult;
    }

    public EngineTurnResult runStockfishTurn(String fen, int elo, int moveNum, int gameId) throws DataAccessException {
        String engineUci = stockFishService.getStockFishMove(fen, elo);
        String engineSan = chessLibService.convertUciToSan(fen, engineUci);
        String engineFen = chessLibService.getUpdatedFen(fen, engineSan);

        saveMove(engineFen, gameId, moveNum, engineSan);
        GameStatus status = chessLibService.getGameStatus(engineFen);

        return new EngineTurnResult(
                engineFen,
                engineSan,
                formatGameEndMessage(status),
                status
        );
    }

    private void updateGameRecord(Game game, String finalFen, boolean peakedAtBoard) throws DataAccessException {
        game.setFen(finalFen);
        if (peakedAtBoard) {
            game.setBoardPeaks(game.getBoardPeaks() + 1);
        }
        gameRepository.update(game);
    }

    private FullTurnDto buildTurnResponse(MoveRequestDto request, PlayerTurnResult playerResult, EngineTurnResult engineResult) {
        int nextMoveNumber = (engineResult.san() != null)
                ? request.moveNumber() + 2
                : request.moveNumber() + 1;

        return new FullTurnDto(
                request.gameId(),
                nextMoveNumber,
                engineResult.finalFen(),
                request.playerSan(),
                engineResult.san(),
                engineResult.message()
        );
    }

    private Result<FullTurnDto> buildIllegalMoveResult(MoveRequestDto request) {
        Result<FullTurnDto> result = new Result<>();
        FullTurnDto errorDto = new FullTurnDto(
                request.gameId(),
                request.moveNumber(),
                request.currentFen(),
                request.playerSan(),
                null,
                "Illegal Move Attempted."
        );
        result.addErrorMessage("Proposed Move not legal in current position.", ResultType.INVALID);
        result.setPayload(errorDto);
        return result;
    }

    public void saveMove(String fenAfter, int gameId, int moveNumber, String moveSan) throws DataAccessException {
        if (moveSan == null) return;
        Move move = new Move();
        move.setFenAfter(fenAfter);
        move.setGameId(gameId);
        move.setMoveNumber(moveNumber);
        move.setMoveSan(moveSan);
        moveRepository.saveMove(move);
    }

    public List<MoveDto> getMovesByGameId(int gameId) throws DataAccessException {
        List<Move> moveList = moveRepository.getMovesByGameId(gameId);
        List<MoveDto> moveDtoList = new ArrayList<>();

        moveList.forEach((move -> {
            MoveDto moveDto = new MoveDto(move.getMoveNumber(), move.getMoveSan(), move.getFenAfter());
            moveDtoList.add(moveDto);
        }));

        return moveDtoList;
    }


    private String formatGameEndMessage(GameStatus status) {
        return switch (status) {
            case WHITE_WIN -> "Game Over: White wins by checkmate!";
            case BLACK_WIN -> "Game Over: Black wins by checkmate!";
            case DRAW_STALEMATE -> "Game Over: Draw by stalemate.";
            case DRAW_50_MOVE_REPETITION -> "Game Over: Draw by 50-move rule.";
            case DRAW_INSUFFICIENT_MATERIAL -> "Game Over: Draw due to insufficient material.";
            case IN_PROGRESS -> null;
        };
    }

    private record PlayerTurnResult(String fen, GameStatus status) {}

    public record EngineTurnResult(String finalFen, String san, String message, GameStatus status) {
        public EngineTurnResult(String finalFen, String san, String message) {
            this(finalFen, san, message, GameStatus.IN_PROGRESS);
        }
    }
}