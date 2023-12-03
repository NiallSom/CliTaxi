import com.ise.taxiapp.entities.Driver;
import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Location;
import com.ise.taxiapp.nav.Point;
import com.ise.taxiapp.nav.Region;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegionTest implements VehicleHiringTest {

    private Region region;

    @Test
    void gridTest() {
        region = new Grid(10, 10);
        assertEquals(0, region.taxiCount());
        assertTrue(testAddToMap("123", new Point(0, 0)));
        assertEquals(1, region.taxiCount());
        assertTrue(testMoveVehicle("123", new Point(1, 1)));
        Location loc = testGetVehicleLoc("123");
        assertEquals(loc, new Point(1, 1));
        assertTrue(testRemoveVehicle("123"));
        assertEquals(0, region.taxiCount());
    }

    /**
     * Inserts the vehicle with registration number reg to the map at location loc if it has not been already added to map.
     * It should return false if the vehicle is not registered or is already on map
     **/
    @Override
    public boolean testAddToMap(String reg, Location loc) {
        if (region.fromReg(reg) != null) return false;
        Taxi taxi = new Taxi("123", new Driver("", ""), Fare.STANDARD_FARE);
        taxi.setLocation(loc);
        assertEquals(loc, taxi.getLocation());
        return region.insertTaxi(taxi);
    }

    /**
     * Update the location of the vehicle with the specified reg number to location loc if vehicle exists and return true.
     * Return false if vehicle not registered or has not been added to the map
     */
    @Override
    public boolean testMoveVehicle(String reg, Location loc) {
        Taxi taxi = region.fromReg(reg);
        if (taxi == null) return false;
        taxi.setLocation(loc);
        assertEquals(loc, taxi.getLocation());
        return true;
    }

    /**
     * Remove the vehicle with the specified reg number from the map if it is registered and return true.
     * If vehicle is not registered or is not on map the method returns false.
     */
    @Override
    public boolean testRemoveVehicle(String reg) {
        return region.removeTaxi(region.fromReg(reg));
    }

    /**
     * Return the location of vehicle specified by the reg number if it is registered and added to the map, null otherwise.
     */
    @Override
    public Location testGetVehicleLoc(String reg) {
        Taxi taxi = region.fromReg(reg);
        if (taxi == null) return null;
        return taxi.getLocation();
    }

    /**
     * Return a list of all vehicles registration numbers located within a square of side 2*r centered at location loc (inclusive
     * of the boundaries).
     */
    @Override
    public List<String> testGetVehiclesInRange(Location loc, int r) {
        // Todo
        return null;
    }
}
