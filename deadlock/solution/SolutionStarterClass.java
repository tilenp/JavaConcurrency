public class SolutionStarterClass {
    
    private void invoke() {
        GoodDispatcher goodDispatcher = new GoodDispatcher();
        GoodTaxi goodTaxi = new GoodTaxi(goodDispatcher);

        // add taxi to dispatcher
        goodDispatcher.addTaxi(goodTaxi);

        // set taxi destination
        TaxiLocation taxiDestination = new TaxiLocation();
        taxiDestination.setCity("New York");
        goodTaxi.setDestination(taxiDestination);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                TaxiLocation taxiLocation = new TaxiLocation();
                taxiLocation.setCity("New York");
                goodTaxi.setLocation(taxiLocation);
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                goodDispatcher.getMap();
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

       printMessage("done");
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        SolutionStarterClass solutionStarterClass = new SolutionStarterClass();
        solutionStarterClass.invoke();
    }
}
