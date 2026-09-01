package learn.blindchess.domain;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import learn.blindchess.model.GameStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChessLibService {

    public String convertUciToSan(String fen, String uciMove) {

        if (uciMove == null || uciMove.length() < 4) {
            throw new IllegalArgumentException("Invalid UCI move format: " + uciMove);
        }

        Board board = new Board();
        board.loadFromFen(fen);

        Square from = Square.fromValue(uciMove.substring(0, 2).toUpperCase());
        Square to = Square.fromValue(uciMove.substring(2, 4).toUpperCase());

        Piece promotion = Piece.NONE;
        if (uciMove.length() == 5) {
            char promoChar = Character.toUpperCase(uciMove.charAt(4));
            boolean isWhite = board.getSideToMove().toString().equals("WHITE");

            promotion = switch (promoChar) {
                case 'Q' -> isWhite ? Piece.WHITE_QUEEN : Piece.BLACK_QUEEN;
                case 'R' -> isWhite ? Piece.WHITE_ROOK : Piece.BLACK_ROOK;
                case 'B' -> isWhite ? Piece.WHITE_BISHOP : Piece.BLACK_BISHOP;
                case 'N' -> isWhite ? Piece.WHITE_KNIGHT : Piece.BLACK_KNIGHT;
                default -> Piece.NONE;
            };
        }

        Move move = new Move(from, to, promotion);

        MoveList moveList = new MoveList(fen);
        moveList.add(move);
        return moveList.toSanArray()[moveList.size()-1];
    }

    public boolean isLegalMove (String proposedSan, String fen){

        if (proposedSan == null || proposedSan.isBlank()) {
            return false;
        }

        Board board = new Board();
        board.loadFromFen(fen);

        List<Move> legalMoves = board.legalMoves();

        for (Move legalMove : legalMoves) {
            MoveList moveList = new MoveList(fen);
            moveList.add(legalMove);
            String legalSan = moveList.toSanArray()[moveList.size()-1];
            if (legalSan.equals(proposedSan)) {
                return true;
            }
        }
        return false;
    }

    public String getUpdatedFen(String fen, String san) {

        Board board = new Board();
        board.loadFromFen(fen);

        board.doMove(san);

        return board.getFen();

    }

    public int getMoveNumber (String fen) {
        Board board = new Board();
        board.loadFromFen(fen);

        return board.getMoveCounter();
    }

    public GameStatus getGameStatus (String fen) {

        Board board = new Board();
        board.loadFromFen(fen);

        if (board.isMated()) {
            return switch (board.getSideToMove()) {
                case WHITE -> GameStatus.BLACK_WIN;
                case BLACK -> GameStatus.WHITE_WIN;
            };
        }

        if (board.isStaleMate()) {
            return GameStatus.DRAW_STALEMATE;
        }

        if (board.getHalfMoveCounter() >= 100) {
            return GameStatus.DRAW_50_MOVE_REPETITION;
        }

        if (board.isInsufficientMaterial()) {
            return GameStatus.DRAW_INSUFFICIENT_MATERIAL;
        }

        return GameStatus.IN_PROGRESS;
    }
}
