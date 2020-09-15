import java.util.HashMap;
import java.util.Map;

public class Storage {

    private final Map<String, Integer> map = new HashMap<>();

    public synchronized void storeDate(String date) {
        Integer count = map.get(date);
        if (count != null) {
            count += 1;
            map.put(date, count);
        } else {
            map.put(date, 1);
        }
    }

    public void printMapSize() {
        System.out.println("map size " + map.size());
    }
}