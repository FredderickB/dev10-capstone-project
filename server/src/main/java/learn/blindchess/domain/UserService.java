package learn.blindchess.domain;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.model.User;

public interface UserService {

    Result<User> processGoogleUser(User user) throws DataAccessException;
}
