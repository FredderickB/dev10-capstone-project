package learn.blindchess.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldUppercaseColor() {
        GameRequestDto dto = new GameRequestDto("white", 1000);
        System.out.println(dto.playerColor());
        assertEquals("WHITE", dto.playerColor());
    }

    @Test
    void shouldResolveRandom() {

        GameRequestDto dto = new GameRequestDto("random", 1000);
        assertTrue(List.of("WHITE", "BLACK").contains(dto.playerColor()));

    }

    @Test
    void shouldDefaultToWhite() {

        GameRequestDto dto = new GameRequestDto(" ", 1000);
        assertEquals("WHITE", dto.playerColor());

    }

    @Test
    void validDataPassValidation() {

        GameRequestDto dto = new GameRequestDto("White", 1000);
        Set<ConstraintViolation<GameRequestDto>> violationSet = validator.validate(dto);

        assertTrue(violationSet.isEmpty());

    }

    @Test
    void nullColorResolvesToWhiteAndPassValidation() {

        GameRequestDto dto = new GameRequestDto(null, 1000);
        Set<ConstraintViolation<GameRequestDto>> violationSet = validator.validate(dto);

        assertTrue(violationSet.isEmpty());

    }

    @Test
    void invalidColorStringFailsValidation() {

        GameRequestDto dto = new GameRequestDto("RED", 1000);
        Set<ConstraintViolation<GameRequestDto>> violationSet = validator.validate(dto);

        assertFalse(violationSet.isEmpty());

    }

    @Test
    void engineLevelOutsideBoundsFailsValidation() {

        GameRequestDto dto = new GameRequestDto("RED", 40000);
        Set<ConstraintViolation<GameRequestDto>> violationSet = validator.validate(dto);

        assertFalse(violationSet.isEmpty());

        dto = new GameRequestDto("RED", -1);
        violationSet = validator.validate(dto);

    }
}