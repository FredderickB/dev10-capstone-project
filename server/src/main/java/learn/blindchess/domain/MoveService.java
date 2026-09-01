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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoveService {

    private MoveRepository moveRepository;
    private GameRepository gameRepository;
    private StockFishService stockFishService;
    private ChessLibService chessLibService;

    public MoveService(MoveRepository moveRepository, GameRepository gameRepository, StockFishService stockFishService, ChessLibService chessLibService) {
        this.moveRepository = moveRepository;
        this.gameRepository = gameRepository;
        this.stockFishService = stockFishService;
        this.chessLibService = chessLibService;
    }

    public Result<FullTurnDto> processPlayerMove(MoveRequestDto moveRequestDto) throws DataAccessException {

        Result<FullTurnDto> result = new Result<>();

        String currentFen = moveRequestDto.currentFen();
        int elo = moveRequestDto.engineLevel();
        String playerSan = moveRequestDto.playerSan();

        Game updatedGame = gameRepository.findById(moveRequestDto.gameId());

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

            String[] fishResult = makeStockFishMove(fenAfterPlayerMove, elo);
            finalFen = fishResult[0];
            engineSan = fishResult[1];

            GameStatus statusAfterEngineMove = chessLibService.getGameStatus(finalFen);
            if (statusAfterEngineMove != GameStatus.IN_PROGRESS) {
                message = formatGameEndMessage(statusAfterEngineMove);
                updatedGame.setStatus(statusAfterEngineMove);
            }
        }


        updatedGame.setFen(finalFen);

        if (moveRequestDto.peakedAtBoard()) {
            updatedGame.setBoardPeaks(updatedGame.getBoardPeaks() + 1);
        }

        gameRepository.update(updatedGame);

        FullTurnDto turnDto = new FullTurnDto(
                moveRequestDto.gameId(),
                moveRequestDto.moveNumber() + 2,
                finalFen,
                playerSan,
                engineSan,
                message
        );

        saveMove(fenAfterPlayerMove, moveRequestDto.gameId(), moveRequestDto.moveNumber(), playerSan);
        saveMove(finalFen, moveRequestDto.gameId(), moveRequestDto.moveNumber(), engineSan);

        result.setPayload(turnDto);
        return result;

    }

    public List<MoveDto> getMovesByGameId(int gameId) throws DataAccessException {

        List<Move> moveList = moveRepository.getMovesByGameId(gameId);

        List<MoveDto> moveDtoList = new ArrayList<>();

        moveList.stream().forEach((move -> {
            MoveDto moveDto = new MoveDto(move.getMoveNumber(), move.getMoveSan(), move.getFenAfter());
            moveDtoList.add(moveDto);
        }));

        return moveDtoList;
    }

    public String[] makeStockFishMove(String fen, int elo) {

        String engineUci = stockFishService.getStockFishMove(fen, elo);
        String engineSan = chessLibService.convertUciToSan(fen, engineUci);
        String updatedFen = chessLibService.getUpdatedFen(fen, engineSan);

        return new String[]{updatedFen, engineSan};
    }

    public void saveMove(String fenAfter, int gameId, int moveNumber, String moveSan) throws DataAccessException {

        Move move = new Move();
        move.setFenAfter(fenAfter);
        move.setGameId(gameId);
        move.setMoveNumber(moveNumber);
        move.setMoveSan(moveSan);
        moveRepository.saveMove(move);

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
