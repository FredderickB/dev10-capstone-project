package learn.blindchess.domain;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.UserRepository;
import learn.blindchess.model.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static learn.blindchess.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Nested
    class processGoogleUser {

        @Test
        void shouldCreateNonexistentUser() throws DataAccessException {

            User newUser = getNewUser();
            newUser.setUserId(3);

            when(repository.create(newUser)).thenReturn(newUser);
            when(repository.findByEmail(newUser.getEmail())).thenReturn(null);

            Result<User> actual = service.processGoogleUser(newUser);
            Result<User> expected = makeSuccessResult(newUser);

            assertEquals(expected, actual);
            verify(repository).create(any());
        }

        @Test
        void shouldUpdateWhenUserIsFound() throws DataAccessException {

            User existingUser = getUserA();
            existingUser.setUsername("new name");

            when(repository.update(existingUser)).thenReturn(existingUser);
            when(repository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);

            Result<User> actual = service.processGoogleUser(existingUser);
            Result<User> expected = makeSuccessResult(existingUser);

            assertEquals(expected, actual);
            verify(repository).update(any());

        }
    }
}