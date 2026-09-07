package learn.blindchess.model;

import java.util.Arrays;

public enum GameStatus {
    IN_PROGRESS(1),
    WHITE_WIN(2),
    BLACK_WIN(3),
    INSUFFICIENT_MATERIAL(4),
    STALEMATE(5),
    FIFTY_MOVE_REPETITION(6);

    private final int statusId;

    GameStatus(int id) {
        this.statusId = id;
    }

    public int getStatusId() {
        return statusId;
    }

    public static GameStatus fromStatusId(int id) {
        return Arrays.stream(values())
                .filter(status -> status.statusId == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status ID: " + id));
    }
}
