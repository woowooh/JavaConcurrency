import java.util.HashSet;
import java.util.Set;

public class PersonSet {

    private final Set<Object> mySet = new HashSet<Object>();

    public synchronized void addPerson(Object o) {
        mySet.add(o);
    }

    public synchronized boolean containsPerson(Object o) {
        return mySet.contains(o);
    }

    public static void main(String[] args) {
        PersonSet s = new PersonSet();
        s.addPerson(1);
    }
}
