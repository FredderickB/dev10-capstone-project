package learn.blindchess.controller;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.MoveService;
import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import learn.blindchess.dto.FullTurnDto;
import learn.blindchess.dto.MoveDto;
import learn.blindchess.dto.MoveRequestDto;
import learn.blindchess.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static learn.blindchess.controller.ErrorResponse.build;

@RestController
@RequestMapping("/api/move")
public class MoveController {

    private MoveService moveService;

    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @PreAuthorize("@gameSecurity.isAuthorized(principal, #moveRequestDto.gameId)")
    @PostMapping
    public ResponseEntity<?> playMove(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody MoveRequestDto moveRequestDto) throws DataAccessException {

        Result<FullTurnDto> result = moveService.processPlayerMove(moveRequestDto);

        if (!result.isSuccess()) {
            return build(result);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @PreAuthorize("@gameSecurity.isAuthorized(principal, #gameId)")
    @GetMapping("/{gameId}")
    public ResponseEntity<?> getMoves(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable int gameId) throws DataAccessException {


        List<MoveDto> moves = moveService.getMovesByGameId(gameId);

        return new ResponseEntity<>(moves, HttpStatus.OK);

    }
}
