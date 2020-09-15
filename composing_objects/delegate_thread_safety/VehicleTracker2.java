import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VehicleTracker2 {

    /** delegate thread safety to immutable point and concurrentHashMap **/

    public class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(Point point) {
            this(point.x, point.y);
        }
    }

    private final ConcurrentHashMap<String, Point> map;
    private final Map<String, Point> unmodifiableMap;


    public VehicleTracker2(Map<String, Point> map) {
        this.map = deepCopy(map);
        unmodifiableMap = Collections.unmodifiableMap(this.map);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String key) {
        return map.get(key); // return an actual object because it is immutable
    }

    public void setLocation(String key, int x, int y) {
        map.replace(key, new Point(x, y));
    }

    private ConcurrentHashMap<String, Point> deepCopy(Map<String, Point> map) {
        ConcurrentHashMap<String, Point> result = new ConcurrentHashMap<>();
        for(Map.Entry<String, Point> entry : map.entrySet()) {
            result.put(entry.getKey(), new Point(entry.getValue()));
        }
        return result;
    }
}