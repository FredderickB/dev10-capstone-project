package learn.blindchess.controller;

import jakarta.validation.Valid;
import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.GameService;
import learn.blindchess.domain.Result;
import learn.blindchess.dto.GameRequestDto;
import learn.blindchess.dto.GameResponseDto;
import learn.blindchess.model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static learn.blindchess.controller.ErrorResponse.build;

@RequestMapping("/api/games")
@RestController
public class GameController {

    private GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createGame(
            @AuthenticationPrincipal Authentication authentication,
            @Valid @RequestBody GameRequestDto requestDto
            ) throws DataAccessException {

        Integer userId = null;

        if (authentication != null && authentication.getPrincipal() instanceof Integer id) {
            userId = id;
        }

        Result<Game> result = service.create(userId, requestDto);

        if (!result.isSuccess()) {
            return build(result);
        } else {
            GameResponseDto dto = new GameResponseDto(result.getPayload());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }


    }
}
