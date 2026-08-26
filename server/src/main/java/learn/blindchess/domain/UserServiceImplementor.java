package learn.blindchess.domain;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.UserRepository;
import learn.blindchess.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImplementor implements UserService {

    private final UserRepository userRepository;

    public UserServiceImplementor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Result<User> findByEmail(String email) throws DataAccessException {

        Result<User> result = new Result<>();
        result.setPayload(userRepository.findByEmail(email));
        return result;

    }

    @Transactional
    public Result<User> processGoogleUser(User user) throws DataAccessException{

        Result<User> result = new Result<>();

        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            result.setPayload(userRepository.update(user));
        } else {
            result.setPayload(userRepository.create(user));
        }

        return result;
    }
}
