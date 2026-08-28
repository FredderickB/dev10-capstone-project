package learn.blindchess.data;

import learn.blindchess.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import static learn.blindchess.TestHelper.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJDBCClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    UserRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Nested
    class findByEmail {

        @Test
        void shouldFind() throws DataAccessException {

            User actual = repository.findByEmail(getUserA().getEmail());
            User expected = getUserA();

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotFind() throws DataAccessException {

            User actual = repository.findByEmail(getNewUser().getEmail());
            assertNull(actual);

        }
    }

    @Nested
    class findById{

        @Test
        void shouldFind() throws DataAccessException {

            User actual = repository.findById(getUserA().getUserId());
            User expected = getUserA();
            assertEquals(expected, actual);

        }

        @Test
        void shouldNotFindNull() throws DataAccessException {

            User actual = repository.findById(null);
            assertNull(actual);

        }
    }

    @Nested
    class create {

        @Test
        void shouldCreate() throws DataAccessException {

            User expected = getNewUser();
            expected.setUserId(3);

            User actual = repository.create(getNewUser());

            assertEquals(expected, actual);

            User actualFound = repository.findByEmail(getNewUser().getEmail());
            assertEquals(expected, actualFound);

        }

    }

    @Nested
    class update {

        @Test
        void shouldUpdate() throws DataAccessException {

            User expected = getUserA();
            expected.setUsername("new name");

            User actual = repository.update(expected);

            assertEquals(expected, actual);

            User actualFound = repository.findByEmail(getUserA().getEmail());
            assertEquals(expected, actualFound);

        }

    }

}