import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Storage {

    private final Map<Integer, LinkedList<Integer>> map = new HashMap<>();

    public synchronized void store(int key, int value) {
        LinkedList<Integer> list = map.get(key);
        if (list == null) {
            LinkedList<Integer> newList = new LinkedList<>();
            newList.add(value);
            map.put(key, newList);
        } else {
            list.add(value);
        }
    }

    public void printMap() {
        for (Map.Entry<Integer, LinkedList<Integer>> entry : map.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (int value : entry.getValue()) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}