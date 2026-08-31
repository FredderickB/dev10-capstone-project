package learn.blindchess.domain;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.MoveRepository;
import learn.blindchess.dto.FullTurnDto;
import learn.blindchess.dto.MoveRequestDto;
import learn.blindchess.model.Game;
import learn.blindchess.model.GameStatus;
import learn.blindchess.model.Move;
import org.springframework.stereotype.Service;

@Service
public class MoveService {

    private MoveRepository moveRepository;
    private GameService gameService;
    private StockFishService stockFishService;
    private ChessLibService chessLibService;

    public MoveService(MoveRepository moveRepository, GameService gameService, StockFishService stockFishService, ChessLibService chessLibService) {
        this.moveRepository = moveRepository;
        this.gameService = gameService;
        this.stockFishService = stockFishService;
        this.chessLibService = chessLibService;
    }

    public Result<FullTurnDto> processPlayerMove(MoveRequestDto moveRequestDto) throws DataAccessException {

        Result<FullTurnDto> result = new Result<>();

        String currentFen = moveRequestDto.currentFen();
        int elo = moveRequestDto.engineLevel();
        String playerSan = moveRequestDto.playerSan();

        Game updatedGame = gameService.findById(moveRequestDto.gameId());

        if (updatedGame == null) {
            result.addErrorMessage("Game id" + moveRequestDto.gameId() + "not found", ResultType.NOT_FOUND);
            return result;
        }
        boolean isLegalMove = chessLibService.isLegalMove(moveRequestDto.playerSan(), currentFen);

        if (!isLegalMove) {
           FullTurnDto errorDto = new FullTurnDto(
                   moveRequestDto.gameId(),
                   moveRequestDto.moveNumber(),
                   currentFen,
                   playerSan,
                   null,
                   "Illegal Move Attempted."
           );
           result.addErrorMessage("Proposed Move not legal in current position.", ResultType.INVALID);
           result.setPayload(errorDto);
           return result;
        }

        String fenAfterPlayerMove = chessLibService.getUpdatedFen(currentFen, playerSan);
        GameStatus gameStatusAfterPlayerMove = chessLibService.getGameStatus(fenAfterPlayerMove);
        String message = null;
        String engineSan = null;
        String finalFen = fenAfterPlayerMove;

        if (gameStatusAfterPlayerMove != GameStatus.IN_PROGRESS) {
            message = formatGameEndMessage(gameStatusAfterPlayerMove);
            updatedGame.setStatus(gameStatusAfterPlayerMove);
        } else {

            String engineUci = stockFishService.getStockFishMove(fenAfterPlayerMove, elo);
            engineSan = chessLibService.convertUciToSan(fenAfterPlayerMove, engineUci);

            finalFen = chessLibService.getUpdatedFen(fenAfterPlayerMove, engineSan);

            GameStatus statusAfterEngineMove = chessLibService.getGameStatus(finalFen);
            if (statusAfterEngineMove != GameStatus.IN_PROGRESS){
                message = formatGameEndMessage(statusAfterEngineMove);
                updatedGame.setStatus(statusAfterEngineMove);
            }
        }


        updatedGame.setFen(finalFen);

        if (moveRequestDto.peakedAtBoard()) {
            updatedGame.setBoardPeaks(updatedGame.getBoardPeaks() + 1);
        }

        gameService.update(updatedGame);

        FullTurnDto turnDto = new FullTurnDto(
                moveRequestDto.gameId(),
                moveRequestDto.moveNumber() + 2,
                finalFen,
                playerSan,
                engineSan,
                message
        );

        saveMove(turnDto);

        result.setPayload(turnDto);
        return result;

    }

    private void saveMove(FullTurnDto fullTurnDto) throws DataAccessException {

        Move playerMove = new Move();
        playerMove.setFenAfter(fullTurnDto.updatedFen());
        playerMove.setGameId(fullTurnDto.gameId());
        playerMove.setMoveNumber(fullTurnDto.moveNumber()-1);
        playerMove.setMoveSan(fullTurnDto.playerSan());
        moveRepository.saveMove(playerMove);

        Move engineMove = new Move();
        engineMove.setFenAfter(fullTurnDto.updatedFen());
        engineMove.setGameId(fullTurnDto.gameId());
        engineMove.setMoveNumber(fullTurnDto.moveNumber()-1);
        engineMove.setMoveSan(fullTurnDto.engineSan());
        moveRepository.saveMove(engineMove);

    }

    private String formatGameEndMessage(GameStatus status) {
        return switch (status) {
            case WHITE_WIN -> "Game Over: White wins by checkmate!";
            case BLACK_WIN -> "Game Over: Black wins by checkmate!";
            case DRAW_STALEMATE -> "Game Over: Draw by stalemate.";
            case DRAW_50_MOVE_REPETITION -> "Game Over: Draw by 50-playerMove rule.";
            case DRAW_INSUFFICIENT_MATERIAL -> "Game Over: Draw due to insufficient material.";
            case IN_PROGRESS -> null;
        };
    }
}
