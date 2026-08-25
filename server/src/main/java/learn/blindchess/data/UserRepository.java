package learn.blindchess.data;

import learn.blindchess.model.User;

public interface UserRepository {

    User findByEmail(String email) throws DataAccessException;

    User save(User user) throws DataAccessException;

}
