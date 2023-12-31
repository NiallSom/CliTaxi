import com.ise.taxiapp.dataStructures.LinkedList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LinkedListTest {

    @Test
    void testList() {
        LinkedList<String> list = new LinkedList<>();
        assertTrue(list.isEmpty());
        list.add("One");
        assertEquals(1, list.size());
        assertEquals("One", list.get());
        list.add("Two");
        assertEquals("Two", list.get());
        list.add("Three");
        list.getPrevious();
        assertEquals("Two", list.get());
        list.remove();
        assertEquals(2, list.size());
        assertEquals("Three", list.get());
        list.findFirst();
        assertEquals("One", list.get());

        // Streams
        assertEquals(2, list.stream().count());

        // ForEach
        var ref = new Object() {
            int count = 0;
        };
        list.forEach(s -> ref.count++);
        assertEquals(2, ref.count);

        list.findFirst();
        list.remove();
        assertEquals(1, list.size());
        assertEquals("Three", list.get());
        // Clearing
        list.clear();
        assertTrue(list.isEmpty());
        assertThrows(UnsupportedOperationException.class, list::get);
        assertThrows(UnsupportedOperationException.class, list::remove);
    }

    @Test
    void removeFirst() {
        LinkedList<String> list = new LinkedList<>();
        list.add("One");
        list.add("Two");
        list.findFirst();
        list.remove();
        assertEquals("Two", list.get());
    }

    @Test
    void removeOnlyElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("One");
        list.remove();
        assertTrue(list.isEmpty());
    }

    @Test
    void removeLast() {
        LinkedList<String> list = new LinkedList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.remove();
        assertEquals("Two", list.get());
    }

    @Test
    void removeOne() {
        LinkedList<String> list = new LinkedList<>();
        list.add("One");
        list.remove();
    }

    @Test
    void removeHeadNamed() {
        LinkedList<String> list = new LinkedList<>();
        list.add("One");
        list.add("Two");
        list.remove("One");
        list.findFirst();
        assertEquals("Two", list.get());
    }

    @Test
    void removeNamed() {
        LinkedList<String> list = new LinkedList<>();
        list.add("One");
        list.add("Two");
        list.remove("Two");
        list.findFirst();
        assertEquals("One", list.get());
    }

    @Test
    void removeSandwichedNamed() {
        LinkedList<String> list = new LinkedList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.remove("Two");
        list.findFirst();
        assertEquals("One", list.get());
        list.getNext();
        assertEquals("Three", list.get());
    }
}