package learn.blindchess.model;

import java.util.Arrays;

public enum PlayerColor {
    WHITE(1),
    BLACK(2);

    private final int playerColorId;

    PlayerColor(int id) {
        this.playerColorId = id;
    }

    public int getPlayerColorId() {
        return playerColorId;
    }

    public static PlayerColor fromPlayerColorId(int id) {
        return Arrays.stream(values())
                .filter(status -> status.playerColorId == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown color ID: " + id));
    }
}
