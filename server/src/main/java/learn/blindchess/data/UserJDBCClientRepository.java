package learn.blindchess.data;

import learn.blindchess.model.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJDBCClientRepository implements UserRepository{

    private final JdbcClient client;

    public UserJDBCClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public User findByEmail(String email) throws DataAccessException {
        String sql = """
                select
                    user_id,
                    email,
                    username
                from user
                where email = ?
                """;

        return client.sql(sql)
                .param(email)
                .query(new UserRowMapper())
                .optional().orElse(null);
    }

    @Override
    public User findById(Integer userId) throws DataAccessException {
        String sql = """
                select
                    user_id,
                    email,
                    username
                from user
                where user_id = ?
                """;

        return client.sql(sql)
                .param(userId)
                .query(new UserRowMapper())
                .optional().orElse(null);
    }


    @Override
    public User create(User user) throws DataAccessException {

        String sql = """
                insert into user (email, username)
                values (:email, :username)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("email", user.getEmail())
                .param("username", user.getUsername())
                .update(keyHolder, "user_id");

        if (rowsAffected == 0) {
            return null;
        }
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataAccessException("Failed to retreive key from database");
        }

        user.setUserId(key.intValue());
        return user;
    }


    public User update(User user) throws DataAccessException {

        String sql = """
            update user
            set username = :username
            where email = :email
            """;

        int rowsAffected = client.sql(sql)
                .param("username", user.getUsername())
                .param("email", user.getEmail())
                .update();

        if (rowsAffected == 0) {
            return null;
        }

        return user;

    }
}
