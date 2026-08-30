package learn.blindchess.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class StockFishServiceTest {

    @Autowired
    StockFishService service;

    @Test
    void serviceRespondsWithUCI1() {

        String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
        int elo = 2500;

        String fishMove = service.getStockFishMove(fen, elo);
        System.out.println(fishMove);

    }

    @Test
    void serviceRespondsWithUCI2() {

        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        int elo = 2500;

        String fishMove = service.getStockFishMove(fen, elo);
        System.out.println(fishMove);

    }
}