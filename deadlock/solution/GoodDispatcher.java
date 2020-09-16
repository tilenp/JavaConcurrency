import java.util.HashSet;
import java.util.Set;

public class GoodDispatcher {

    private Set<GoodTaxi> taxis;
    private Set<GoodTaxi> availableTaxis;

    public GoodDispatcher() {
        taxis = new HashSet<>();
        availableTaxis = new HashSet<>();
    }

    public synchronized void addTaxi(GoodTaxi taxi) {
        taxis.add(taxi);
    }

    public synchronized void taxiBecomeAvailable(GoodTaxi taxi) {
        availableTaxis.add(taxi);
    }

    // release lock before calling another method to avoid potential deadlock
    public GpsMap getMap() {
        Set<GoodTaxi> copy;
        synchronized (this) {
            printMessage(Thread.currentThread().getName() + " acquired dispatcher lock");

            // increase the window of vulnerability
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            copy = new HashSet<>(taxis);
        }

        printMessage(Thread.currentThread().getName() + " release dispatcher lock");

        GpsMap map = new GpsMap();
        for (GoodTaxi taxi : copy) {
            printMessage(Thread.currentThread().getName() + " try to acquire taxi lock");
            map.drawMarker(taxi.getLocation());
            printMessage(Thread.currentThread().getName() + " release taxi lock");
        }
        return map;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
