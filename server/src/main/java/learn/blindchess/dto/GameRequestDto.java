package learn.blindchess.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record GameRequestDto (
    @NotNull(message = "Player color is required.")
    @Pattern(regexp = "^(?i)(WHITE|BLACK|RANDOM)$", message = "Player color must be WHITE, BLACK, or RANDOM.")
    String playerColor,

    @Min(value = 400, message = "Engine level must be at least 400.")
    @Max(value = 2500, message = "Engine level cannot exceed 2500.")
    int engineLevel){

    public GameRequestDto {
        if (playerColor == null || playerColor.isBlank()) {
            playerColor = "WHITE";
        } else if ("RANDOM".equalsIgnoreCase(playerColor.trim())) {
            playerColor = Math.random() < 0.5 ? "WHITE" : "BLACK";
        } else {
            playerColor = playerColor.trim().toUpperCase();
        }
    }
}
