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

    @Test
    void save() {
    }
}