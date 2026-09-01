package learn.blindchess.dto;

public record MoveDto (
        int moveNumber,
        String moveSan,
        String fenAfter
){
}
