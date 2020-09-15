import java.util.HashSet;
import java.util.Set;

public class BadDispatcher {
    
    private Set<BadTaxi> taxis;
    private Set<BadTaxi> availableTaxis;

    public BadDispatcher() {
        taxis = new HashSet<>();
        availableTaxis = new HashSet<>();
    }

    public synchronized void addTaxi(BadTaxi taxi) {
        taxis.add(taxi);
    }

    public synchronized void taxiBecomeAvailable(BadTaxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized GpsMap getMap() {
        System.out.println(Thread.currentThread().getName() + " acquired dispatcher lock");

        // increase window of vulnerability
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GpsMap map = new GpsMap();
        for (BadTaxi taxi : taxis) {
            System.out.println(Thread.currentThread().getName() + " try to acquire taxi lock");
            map.drawMarker(taxi.getLocation());
        }
        return map;
    }
}