package learn.blindchess.domain;

import learn.blindchess.data.DataAccessException;
import learn.blindchess.data.UserRepository;
import learn.blindchess.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Result<User> findById(int userId) throws DataAccessException {

        Result<User> result = new Result<>();
        result.setPayload(userRepository.findById(userId));

        if (result.getPayload() == null) {
            result.addErrorMessage("User not found", ResultType.NOT_FOUND);
        }

        return result;

    }

    @Transactional
    public Result<User> processGoogleUser(User user) throws DataAccessException{

        Result<User> result = new Result<>();

        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            result.setPayload(existingUser);
        } else {

            User newUser = userRepository.create(user);

            if (newUser == null || newUser.getUserId() == null) {
                result.addErrorMessage("Failed to create user in database", ResultType.SERVER_ERROR);
                return result;
            }
            result.setPayload(newUser);
        }

        return result;
    }
}
