import com.ise.taxiapp.dataStructures.LinkedList;

public class LinkedListTest {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.insert("Pizza1");
        list.insert("Pizza2");
        list.insert("Pizza3");
        list.insert("Pizza4");
        list.insert("Pizza5");
        list.insert("Pizza6");
        list.insert("Pizza7");
        list.getPrevious();
        list.remove();
        list.printList();
        System.out.println(list.retrieve());
        list.getPrevious();
        list.getPrevious();
        System.out.println(list.retrieve());
    }
}
