package learn.blindchess.dto;

import learn.blindchess.model.Game;

import java.time.LocalDateTime;

public record GameSummaryDto (
         int gameId,
         int engineLevel,
         int boardPeaks,
         String status,
         String playerColor,
         int totalMoves,
         LocalDateTime createdAt
) {

    public GameSummaryDto(Game game, int moveCount) {
        this(game.getGameId(), game.getEngineLevel(), game.getBoardPeaks(), game.getStatus().name(), game.getPlayerColor().name(), moveCount, game.getCreatedAt());
    }
}