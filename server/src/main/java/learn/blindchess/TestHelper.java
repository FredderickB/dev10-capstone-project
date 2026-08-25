package learn.blindchess;

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

}
