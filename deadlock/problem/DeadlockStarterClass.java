public class DeadlockStarterClass {

    public void invoke() {
        BadDispatcher badDispatcher = new BadDispatcher();
        BadTaxi badTaxi = new BadTaxi(badDispatcher);
    
        // add taxi to dispatcher
        badDispatcher.addTaxi(badTaxi);
    
        // set taxi destination
        TaxiLocation taxiDestination = new TaxiLocation();
        taxiDestination.setCity("New York");
        badTaxi.setDestination(taxiDestination);
    
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                TaxiLocation taxiLocation = new TaxiLocation();
                taxiLocation.setCity("New York");
                badTaxi.setLocation(taxiLocation);
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                badDispatcher.getMap();
            }
        };
    
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
    
        thread1.start();
        thread2.start();
    
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DeadlockStarterClass deadlockStarterClass = new DeadlockStarterClass();
        deadlockStarterClass.invoke();
    }
}