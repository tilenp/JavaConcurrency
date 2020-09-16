public class GoodTaxi {
    
    private TaxiLocation location;
    private TaxiLocation destination;
    private final GoodDispatcher dispatecher;

    public GoodTaxi(GoodDispatcher dispatecher) {
        this.dispatecher = dispatecher;
    }

    public synchronized TaxiLocation getLocation() {
        return location;
    }

    public synchronized void setDestination(TaxiLocation destination) {
        this.destination = destination;
    }

    // release lock before calling another method to avoid potential deadlock
    public void setLocation(TaxiLocation location) {
        boolean reachedDestination;
        synchronized (this) {
            printMessage(Thread.currentThread().getName() + " acquired taxi lock");

            // increase the window of vulnerability
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.location = location;
            reachedDestination = location.getCity().equals(destination.getCity());
        }

        printMessage(Thread.currentThread().getName() + " release taxi lock");

        if (reachedDestination) {
            printMessage(Thread.currentThread().getName() + " try to acquire dispatcher lock");
            dispatecher.taxiBecomeAvailable(this);
            printMessage(Thread.currentThread().getName() + " release dispatcher lock");
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
