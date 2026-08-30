package learn.blindchess.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ChessLibServiceTest {

    @Autowired
    private ChessLibService service;

    private static final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    @Nested
    class StandardMoves {

        @Test
        void testPawnMove() {
            String san = service.convertUciToSan(STARTING_FEN, "e2e4");
            assertEquals("e4", san);
        }

        @Test
        void testKnightMove() {
            String san = service.convertUciToSan(STARTING_FEN, "g1f3");
            assertEquals("Nf3", san);
        }
    }

    @Nested
    class Captures {

        @Test
        void testPieceCapture() {
            String fen = "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3";
            String san = service.convertUciToSan(fen, "f3e5");
            assertEquals("Nxe5", san);
        }

        @Test
        void testPawnCapture() {
            String fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2";
            String san = service.convertUciToSan(fen, "e4d5");
            assertEquals("exd5", san);
        }

        @Test
        void testEnPassant() {
            String fen = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3";
            String san = service.convertUciToSan(fen, "e5f6");
            assertEquals("exf6", san);
        }
    }

    @Nested
    class SpecialMoves {

        @ParameterizedTest(name = "UCI {0} should promote to SAN {1}")
        @CsvSource({
                "e7e8q, e8=Q",
                "e7e8r, e8=R",
                "e7e8b, e8=B",
                "e7e8n, e8=N"
        })
        void testPromotions(String uciMove, String expectedSan) {
            String promoFen = "8/4P3/8/8/8/8/8/3k3K w - - 0 1";
            String san = service.convertUciToSan(promoFen, uciMove);
            assertEquals(expectedSan, san);
        }
    }

    @Nested
    class ExceptionHandling {

        @Test
        void testInvalidUciString() {
            assertThrows(IllegalArgumentException.class, () ->
                    service.convertUciToSan(STARTING_FEN, "e2")
            );
            assertThrows(IllegalArgumentException.class, () ->
                    service.convertUciToSan(STARTING_FEN, null)
            );
        }
    }

}