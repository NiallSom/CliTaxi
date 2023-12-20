import com.ise.taxiapp.entities.*;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaxiTest {

    @Test
    void testDriveToDestinationDifferentInitialLocation() {
        Point initialLocation = new Point(2, 3); // Change initial location
        Point destination = new Point(5, 5);
        Taxi taxi = new Taxi("ABC123", new Driver("John", "D123"), Fare.STANDARD_FARE);
        taxi.setLocation(initialLocation);
        taxi.setDestination(destination);
        taxi.driveToDestination();
        assertEquals(destination, taxi.getLocation());
    }

    @Test
    void testCalculateChargeDifferentDistances() {
        Point initialLocation = new Point(0, 0);
        Point destination1 = new Point(5, 5);
        Point destination2 = new Point(10, 10);
        Taxi taxi1 = new Taxi("XYZ456", new Driver("Alice", "D456"), Fare.STANDARD_FARE);
        taxi1.setLocation(initialLocation);
        taxi1.setDestination(destination1);
        taxi1.driveToDestination();
        double expectedCharge1 = Fare.STANDARD_FARE.calculateCharge(initialLocation.distanceTo(destination1));
        assertEquals(expectedCharge1, taxi1.calculateCharge());

        Taxi taxi2 = new Taxi("DEF789", new Driver("Bob", "D789"), Fare.EXTRA_LARGE_FARE);
        taxi2.setLocation(initialLocation);
        taxi2.setDestination(destination2);
        taxi2.driveToDestination();
        double expectedCharge2 = Fare.EXTRA_LARGE_FARE.calculateCharge(initialLocation.distanceTo(destination2));
        assertEquals(expectedCharge2, taxi2.calculateCharge());
    }

    @Test
    void testMarkAsAvailableAfterServingUser() {
        Point initialLocation = new Point(0, 0);
        Point destination = new Point(5, 5);
        Taxi taxi = new Taxi("ABC123", new Driver("John", "D123"), Fare.STANDARD_FARE);
        taxi.setLocation(initialLocation);
        taxi.setDestination(destination);
        taxi.driveToDestination();
        taxi.setUser(new User("Alice")); // Taxi served a user
        taxi.markAsAvailable();
        assertEquals(TaxiStatus.AVAILABLE, taxi.getStatus());
        assertNull(taxi.getUser());
    }

    @Test
    void testSetDestinationMultipleTimes() {
        Point initialLocation = new Point(0, 0);
        Point destination1 = new Point(5, 5);
        Point destination2 = new Point(10, 10);
        Taxi taxi = new Taxi("ABC123", new Driver("John", "D123"), Fare.STANDARD_FARE);
        taxi.setLocation(initialLocation);
        taxi.setDestination(destination1);
        taxi.driveToDestination();
        assertEquals(destination1, taxi.getLocation());

        taxi.setDestination(destination2); // Change destination
        taxi.driveToDestination();
        assertEquals(destination2, taxi.getLocation());
    }
}
