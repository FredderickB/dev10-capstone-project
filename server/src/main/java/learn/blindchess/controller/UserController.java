package learn.blindchess.controller;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.domain.Result;
import learn.blindchess.domain.UserService;
import learn.blindchess.dto.GameResponseDto;
import learn.blindchess.dto.UsernameDto;
import learn.blindchess.model.Game;
import learn.blindchess.model.User;
import learn.blindchess.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static learn.blindchess.controller.ErrorResponse.build;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<?> getUsername(
            @AuthenticationPrincipal UserPrincipal user) throws DataAccessException {

        Result<User> result = userService.findById(user.getUserId());

        if (!result.isSuccess()) {
            return build(result);
        }

        UsernameDto dto = new UsernameDto(result.getPayload().getUsername());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
