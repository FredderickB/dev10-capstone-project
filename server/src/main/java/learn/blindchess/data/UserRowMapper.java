package learn.blindchess.data;

import learn.blindchess.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getObject("user_id", Integer.class),
                rs.getString("username"),
                rs.getString("email")
        );
    }
}
