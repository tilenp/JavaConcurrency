public class BadTaxi {

    private TaxiLocation location;
    private TaxiLocation destination;
    private final BadDispatcher dispatcher;

    public BadTaxi(BadDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized TaxiLocation getLocation() {
        return location;
    }

    public synchronized void setDestination(TaxiLocation destination) {
        this.destination = destination;
    }

    public synchronized void setLocation(TaxiLocation location) {
        System.out.println(Thread.currentThread().getName() + " acquired taxi lock");

        // increase window of vulnerability
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.location = location;
        if (location.getCity().equals(destination.getCity())) {
            System.out.println(Thread.currentThread().getName() + " try to acquire dispatcher lock");
            dispatcher.taxiBecomeAvailable(this);
        }
    }
}