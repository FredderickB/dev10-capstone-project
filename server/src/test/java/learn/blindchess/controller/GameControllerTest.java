package learn.blindchess.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.GameService;
import learn.blindchess.domain.Result;
import learn.blindchess.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static learn.blindchess.TestHelper.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @MockitoBean
    GameService service;

    @Autowired
    MockMvc mvc;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void shouldCreateValidGame() throws Exception {

        String requestJson = """
                {
                    "PlayerColor": "White",
                    "engineLevel": 1000
                }
                """;

        Game validGame = getGameA();
        Result<Game> expectedResult = makeSuccessResult(validGame);

        when(service.create(null, getNewGameRequestDto())).thenReturn(expectedResult);

        MockHttpServletRequestBuilder request = post("/api/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonMapper.writeValueAsString(validGame)));

    }
}