package learn.blindchess.data;

import learn.blindchess.model.User;
import org.springframework.jdbc.core.simple.JdbcClient;
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
    public User save(User user) throws DataAccessException {
        return null;
    }
}
