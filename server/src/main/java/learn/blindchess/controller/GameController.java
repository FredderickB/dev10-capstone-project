package learn.blindchess.controller;

import jakarta.validation.Valid;
import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.GameService;
import learn.blindchess.domain.Result;
import learn.blindchess.dto.GameRequestDto;
import learn.blindchess.dto.GameResponseDto;
import learn.blindchess.dto.GameSummaryDto;
import learn.blindchess.model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @AuthenticationPrincipal Integer userId,
            @Valid @RequestBody GameRequestDto requestDto
    ) throws DataAccessException {

        Result<Game> result = service.create(userId, requestDto);

        if (!result.isSuccess()) {
            return build(result);
        } else {
            GameResponseDto dto = new GameResponseDto(result.getPayload());
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }

    }

    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGame(
            @AuthenticationPrincipal Integer userId,
            @PathVariable int gameId) throws DataAccessException {

        // todo: authorize user

        Game gameFound = service.findById(gameId);

        if (gameFound != null) {
            GameResponseDto dto = new GameResponseDto(gameFound);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        return new ResponseEntity<>("game not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<?> deleteGame(
            @AuthenticationPrincipal Integer userId,
            @PathVariable int gameId) throws DataAccessException {

        // todo: authorize user

        boolean result = service.delete(gameId);

        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("game not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<?> getAllGames(
            @AuthenticationPrincipal Integer userId
            ) throws DataAccessException {

        // todo: authorize user

        if (userId == null) {
            return new ResponseEntity<>("User Id null", HttpStatus.BAD_REQUEST);
        }

        List<GameSummaryDto> gamesFound = service.findAllByUserId(userId);

        if (gamesFound != null) {
            return new ResponseEntity<>(gamesFound, HttpStatus.OK);
        }

        return new ResponseEntity<>("games not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{gameId}/resign")
    public ResponseEntity<?> resignGame(
            @AuthenticationPrincipal Authentication authentication,
            @PathVariable int gameId
    ) throws DataAccessException {

        Integer userId = null;

        // todo: authorize user

        if (authentication != null && authentication.getPrincipal() instanceof Integer id) {
            userId = id;
        }

        Game gameFound = service.findById(gameId);

        if (gameFound == null) {
            return new ResponseEntity<>("game not found", HttpStatus.NOT_FOUND);
        }

        boolean result = service.resign(gameFound);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
