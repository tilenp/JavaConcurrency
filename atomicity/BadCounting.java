import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BadCounting {
    
    private int count = 0;

    public void invoke() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.submit(runnable);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printMessage("done " + count);
    }

    private void increment() {
        count++;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        BadCounting badCounting = new BadCounting();
        badCounting.invoke();
    }
}