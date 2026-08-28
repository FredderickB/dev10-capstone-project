package learn.blindchess.model;

import java.util.Arrays;

public enum GameStatus {
    WHITE_WIN(1),
    BLACK_WIN(2),
    DRAW(3),
    IN_PROGRESS(4);

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
