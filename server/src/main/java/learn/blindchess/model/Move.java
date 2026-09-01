package learn.blindchess.model;

import java.util.Objects;

public class Move {

    private Integer moveId;
    private int gameId;
    private int moveNumber;
    private String moveSan;
    private String fenAfter;

    public Move(Move move) {
        this.moveId = move.getMoveId();
        this.gameId = move.getGameId();
        this.moveNumber = move.getMoveNumber();
        this.moveSan = move.getMoveSan();
        this.fenAfter = move.getFenAfter();
    }

    public Move(Integer moveId, int gameId, int moveNumber, String moveSan, String fenAfter) {
        this.moveId = moveId;
        this.gameId = gameId;
        this.moveNumber = moveNumber;
        this.moveSan = moveSan;
        this.fenAfter = fenAfter;
    }

    public Move() {
    }

    public Integer getMoveId() {
        return moveId;
    }

    public void setMoveId(Integer moveId) {
        this.moveId = moveId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public String getMoveSan() {
        return moveSan;
    }

    public void setMoveSan(String moveSan) {
        this.moveSan = moveSan;
    }

    public String getFenAfter() {
        return fenAfter;
    }

    public void setFenAfter(String fenAfter) {
        this.fenAfter = fenAfter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return moveId == move.moveId && gameId == move.gameId && moveNumber == move.moveNumber && Objects.equals(moveSan, move.moveSan) && Objects.equals(fenAfter, move.fenAfter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveId, gameId, moveNumber, moveSan, fenAfter);
    }

    @Override
    public String toString() {
        return "Move{" +
                "moveId=" + moveId +
                ", gameId=" + gameId +
                ", moveNumber=" + moveNumber +
                ", moveSan='" + moveSan + '\'' +
                ", fenAfter='" + fenAfter + '\'' +
                '}';
    }
}
