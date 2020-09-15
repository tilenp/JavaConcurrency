import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class WhenDelegatingSafetyFails {

    /**
     * if state variables are independent thread safe classes no additional synchronization is needed
     * a class can delegate thread safety to them
     * <p>
     * can not delegate thread safety to 2 thread safe classes because they are not independent
     * need to use synchronization
     **/
    private Random random = new Random();

    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(100);

    public void invoke() {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    setLower(random.nextInt(100));
                    if (boundsNotValid()) {
                        printMessage("bounds not valid");
                    }
                }
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    setUpper(random.nextInt(100));
                    if (boundsNotValid()) {
                        printMessage("bounds not valid");
                    }
                }
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread2.start();
        thread1.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("done");
    }

    private void setLower(int i) {
        if (i < upper.get()) {
            lower.set(i);
        }
    }

    private void setUpper(int i) {
        if (i > lower.get()) {
            upper.set(i);
        }
    }

    private boolean boundsNotValid() {
        return lower.get() > upper.get();
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        WhenDelegatingSafetyFails whenDelegatingSafetyFails = new WhenDelegatingSafetyFails();
        whenDelegatingSafetyFails.invoke();
    }
}