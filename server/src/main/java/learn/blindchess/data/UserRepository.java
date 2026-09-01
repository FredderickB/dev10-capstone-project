package learn.blindchess.data;

import learn.blindchess.model.User;

public interface UserRepository {

    User findByEmail(String email) throws DataAccessException;

    User findById(Integer userId) throws DataAccessException;

    User create(User user) throws DataAccessException;

    User update(User user) throws DataAccessException;

}
