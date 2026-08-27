package learn.blindchess.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Game {

    private Integer gameId;
    private int engineLevel;
    private String fen;
    private GameStatus status;
    private LocalDateTime createdAt;
    private int boardPeaks;
    private User user;

    public Game(Game game) {
        this.gameId = game.getGameId();
        this.engineLevel = game.getEngineLevel();
        this.status = game.getStatus();
        this.createdAt = game.getCreatedAt();
        this.boardPeaks = game.getBoardPeaks();
        this.user = game.getUser();
        this.fen = game.getFen();
    }

    public Game() {
    }

    public Game(Integer gameId, int engineLevel, String fen, LocalDateTime createdAt, GameStatus status, int boardPeaks, User user) {
        this.gameId = gameId;
        this.engineLevel = engineLevel;
        this.fen = fen;
        this.createdAt = createdAt;
        this.status = status;
        this.boardPeaks = boardPeaks;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId && engineLevel == game.engineLevel && boardPeaks == game.boardPeaks && Objects.equals(fen, game.fen) && status == game.status && Objects.equals(createdAt, game.createdAt) && Objects.equals(user, game.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, engineLevel, fen, status, createdAt, boardPeaks, user);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", engineLevel=" + engineLevel +
                ", fen='" + fen + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", boardPeaks=" + boardPeaks +
                ", user=" + user +
                '}';
    }
}
