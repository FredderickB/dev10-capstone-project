package learn.blindchess.dto;

public record FullTurnDto(
        int gameId,
        int moveNumber,
        String updatedFen,
        String playerSan,
        String engineSan,
        String message
) {
}
