public class BadInitialization {

    private ExpensiveObject instance = null;
    private ExpensiveObject object1;
    private ExpensiveObject object2;

    public void invoke() {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                object1 = getInstance();
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                object2 = getInstance();
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

        printMessage("object1 == object2 " + (object1 == object2));
    }

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        BadInitialization badInitialization = new BadInitialization();
        badInitialization.invoke();
    }
}