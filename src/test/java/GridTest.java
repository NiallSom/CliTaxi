import com.ise.taxiapp.entities.*;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class GridTest {

    @Test
    void testMoveTaxiRandomly() {
        Grid grid = new Grid(10, 10); // Creating a 10x10 grid
        Taxi taxi = new Taxi("ABC123", new Driver("John", "D123"), Fare.STANDARD_FARE);
        taxi.setLocation(grid.get(5, 5)); // Place the taxi at position (5, 5)
        taxi.setStatus(TaxiStatus.AVAILABLE);

        // Move the taxi randomly
        grid.moveTaxiRandomly(taxi);

        // Ensure that the taxi's location has changed
        assertNotEquals(grid.get(5, 5), taxi.getLocation());
    }

}
