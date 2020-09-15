import java.util.HashMap;
import java.util.Map;

public class VehicleTracker1 {

    /**
     * 
     * getters and setters need to be synchronized because we are not using ConcurrentHashMap  
     * if live data is required content of the map should be immutable return Collections.unmodifiableMap(map)
     * if screen shot is enough return deep copy, content can be mutable
     *
     * this class is thread safe because all the paths to the state are synchronized and it returns unmodifiable collection with immutable content
     */

    public class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(Point point) {
            this(point.x, point.y);
        }
    }

    private final Map<String, Point> map;

    // Point object is mutable but this class is thread safe because it never publishes the map or point objects
    // it always publishes a deep copy
    // constructor creates its own copy of the map!!
    public VehicleTracker1(Map<String, Point> map) {
        this.map = deepCopy(map);
    }

    public synchronized Map<String, Point> getLocations() {
        return deepCopy(map); // return a screen shot
    }

    public synchronized Point getLocation(String key) {
        Point point = map.get(key);
        return point != null ? new Point(point) : null; // return a copy of the actual object
    }

    public synchronized void setLocation(String key, int x, int y) {
        map.put(key, new Point(x, y));
    }

    private Map<String, Point> deepCopy(Map<String, Point> map) {
        Map<String, Point> result = new HashMap<>();
        for(String key : map.keySet()) {
            result.put(key, new Point(map.get(key)));
        }
        return result;
    }
}