package learn.blindchess;

import learn.blindchess.domain.Result;
import learn.blindchess.domain.ResultType;
import learn.blindchess.model.User;

public class TestHelper {

    private static final User USER_A = new User(1, "a", "a@a.com");
    private static final User USER_B = new User(2, "b", "b@b.com");
    private static final User NEW_USER = new User(null, "c", "c@c.com");

    public static User getUserA() {
        return new User(USER_A);
    }
    public static User getUserB() {
        return new User(USER_B);
    }
    public static User getNewUser() {
        return new User(NEW_USER);
    }

    public static <T> Result<T> makeSuccessResult(T payload) {

        Result<T> result = new Result<>();
        result.setPayload(payload);
        return result;

    }

    public static <T> Result<T> makeInvalidResult(String message) {

        Result<T> result = new Result<>();
        result.addErrorMessage(message, ResultType.INVALID);
        return result;

    }

    public static <T> Result<T> makeNotFoundResult(String message) {

        Result<T> result = new Result<>();
        result.addErrorMessage(message, ResultType.NOT_FOUND);
        return result;

    }

}
