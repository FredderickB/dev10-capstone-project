package learn.blindchess.dto;

public record MoveRequestDto(
        int gameId,
        int moveNumber,
        String playerSan,
        int engineLevel,
        String currentFen,
        boolean peakedAtBoard
) {

}
