import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {

    private int numberOfPermits = 3;
    private Semaphore semaphore = new Semaphore(numberOfPermits);

    public void invoke() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runner());
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printMessage("done");
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private class Runner implements Runnable {

        @Override
        public void run() {
            try {
                semaphore.acquire();
                printMessage(Thread.currentThread().getName() + " working...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }

            printMessage("task finished");
        }
    }

    public static void main(String[] args) {
        SemaphoreExample semaphoreExample = new SemaphoreExample();
        semaphoreExample.invoke();
    }
}