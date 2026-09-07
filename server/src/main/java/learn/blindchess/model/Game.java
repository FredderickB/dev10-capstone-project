package learn.blindchess.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Game {

    private Integer gameId;
    private int engineLevel;
    private String fen;
    private GameStatus status;
    private PlayerColor playerColor;
    private LocalDateTime createdAt;
    private int boardPeaks;
    private Integer userId;
    private boolean isDeleted;
    private String guestId;

    public Game(Game game) {
        this.gameId = game.getGameId();
        this.engineLevel = game.getEngineLevel();
        this.status = game.getStatus();
        this.createdAt = game.getCreatedAt();
        this.boardPeaks = game.getBoardPeaks();
        this.playerColor = game.getPlayerColor();
        this.userId = game.getUserId();
        this.fen = game.getFen();
        this.isDeleted = game.isDeleted();
        this.guestId = game.getGuestId();
    }

    public Game() {
    }

    public Game(Integer gameId, int engineLevel, String fen, LocalDateTime createdAt, GameStatus status,PlayerColor playerColor, int boardPeaks, Integer userId, String guestId) {
        this.gameId = gameId;
        this.engineLevel = engineLevel;
        this.fen = fen;
        this.createdAt = createdAt;
        this.status = status;
        this.playerColor = playerColor;
        this.boardPeaks = boardPeaks;
        this.userId = userId;
        this.guestId = guestId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getEngineLevel() {
        return engineLevel;
    }

    public void setEngineLevel(int engineLevel) {
        this.engineLevel = engineLevel;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getBoardPeaks() {
        return boardPeaks;
    }

    public void setBoardPeaks(int boardPeaks) {
        this.boardPeaks = boardPeaks;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return engineLevel == game.engineLevel && boardPeaks == game.boardPeaks && isDeleted == game.isDeleted && Objects.equals(gameId, game.gameId) && Objects.equals(fen, game.fen) && status == game.status && playerColor == game.playerColor && Objects.equals(createdAt, game.createdAt) && Objects.equals(userId, game.userId) && Objects.equals(guestId, game.guestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, engineLevel, fen, status, playerColor, createdAt, boardPeaks, userId, isDeleted, guestId);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", engineLevel=" + engineLevel +
                ", fen='" + fen + '\'' +
                ", status=" + status +
                ", playerColor=" + playerColor +
                ", createdAt=" + createdAt +
                ", boardPeaks=" + boardPeaks +
                ", userId=" + userId +
                ", isDeleted=" + isDeleted +
                ", guestId='" + guestId + '\'' +
                '}';
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
