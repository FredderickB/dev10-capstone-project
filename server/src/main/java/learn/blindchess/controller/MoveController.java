package learn.blindchess.controller;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.MoveService;
import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import learn.blindchess.dto.FullTurnDto;
import learn.blindchess.dto.MoveRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static learn.blindchess.controller.ErrorResponse.build;

@RestController
@RequestMapping("/api/move")
public class MoveController {

    private MoveService moveService;

    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @PostMapping
    public ResponseEntity<?> playMove(@RequestBody MoveRequestDto moveRequestDto) throws DataAccessException {

        Result<FullTurnDto> result = moveService.processPlayerMove(moveRequestDto);

        if (!result.isSuccess()) {
            return build(result);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }
}
