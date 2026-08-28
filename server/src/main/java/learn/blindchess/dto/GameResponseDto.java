package learn.blindchess.dto;

import learn.blindchess.model.Game;

public record GameResponseDto (
        int gameId,
        String fen,
        String playerColor,
        String status,
        int engineLevel
){

    public GameResponseDto(Game game) {
        this(game.getGameId(), game.getFen(), game.getPlayerColor().toString(), game.getStatus().toString(), game.getEngineLevel());
    }
}
