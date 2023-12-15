import com.ise.taxiapp.entities.User;
import com.ise.taxiapp.nav.Grid;
import com.ise.taxiapp.nav.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NavTest {

    private static Grid grid;

    @BeforeAll
    static void init() {
        grid = new Grid(10, 10);
    }

    @Test
    void adjacent() {
        Point origin = grid.get(5, 5);
        Point goal = grid.get(5, 6);
        goal.getObjects().add(new User("Name"));
        Object result = grid.findNearest(origin, new User("Name")::equals, 1);
        Assertions.assertEquals(new User("Name"), result);
    }

    @Test
    void twoAway() {
        Point origin = grid.get(3, 2);
        Point goal = grid.get(5, 2);
        goal.getObjects().add(new User("Name"));
        Object result = grid.findNearest(origin, new User("Name")::equals, 2);
        Assertions.assertEquals(new User("Name"), result);
    }


    @Test
    void diagonal() {
        Point origin = grid.get(3, 3);
        Point goal = grid.get(4, 4);
        goal.getObjects().add(new User("Name"));
        Object result = grid.findNearest(origin, new User("Name")::equals, 2);
        Assertions.assertEquals(new User("Name"), result);
    }

    @Test
    void smallRadius() {
        Point origin = grid.get(3, 3);
        Point goal = grid.get(3, 5);
        goal.getObjects().add(new User("Name"));
        Object result = grid.findNearest(origin, new User("Name")::equals, 1);
        Assertions.assertNull(result);

        Object result2 = grid.findNearest(origin, new User("Name")::equals, 2);
        Assertions.assertNotNull(result2);

        Object result3 = grid.findNearest(origin, new User("Name")::equals, 3);
        Assertions.assertNotNull(result3);
    }


    @Test
    void edgeOfGrid() {
        Point origin = grid.get(0, 0);
        Point goal = grid.get(0, 1);
        goal.getObjects().add(new User("Name"));
        Object result = grid.findNearest(origin, new User("Name")::equals, 1);
        Assertions.assertEquals(new User("Name"), result);
    }

    @Test
    void outOfBounds() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> grid.get(-1, 0));
    }

}
