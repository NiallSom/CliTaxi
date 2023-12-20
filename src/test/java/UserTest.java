import com.ise.taxiapp.entities.User;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {
    @Test
    void testUserInitialization() {
        String username = "Alice";
        User user = new User(username);
        assertEquals(username, user.getUsername());
        assertEquals(20.0, user.getBalance());
    }

    @Test
    void testCharge() {
        double initialBalance = 20.0;
        double chargeAmount = 10.0;
        User user = new User("Bob");
        user.charge(chargeAmount);
        assertEquals(initialBalance - chargeAmount, user.getBalance());
    }

    @Test
    void testSetLocation() {
        User user = new User("Charlie");
        Point location = new Point(5, 5);
        user.setLocation(location);
        assertEquals(location, user.getLocation());
    }

    @Test
    void testEqualsMethod() {
        User user1 = new User("David");
        User user2 = new User("David");
        User user3 = new User("Eve");

        assertEquals(user1, user2); // Test equality for users with the same username
        assertNotEquals(user1, user3); // Test inequality for users with different usernames
    }
}
