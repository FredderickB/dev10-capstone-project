package learn.blindchess.domain;

import learn.blindchess.data.MoveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoveServiceTest {

    @Autowired
    private MoveRepository moveRepository;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
    private StockFishService stockFishService;

    @MockitoBean
    private ChessLibService chessLibService;

    @Test
    void shouldHandleLegalMove() {
    }

    @Test
    void shouldHandleIllegalMove() {
    }

    @Test
    void shouldHandlePlayerWin() {
    }

    @Test
    void shouldHandleEngineWin() {
    }
}