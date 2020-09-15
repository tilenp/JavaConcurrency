import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {

    public void invoke() {
        try {
            timeTask(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void timeTask(final int numberOfThreads) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(numberOfThreads);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(new Runner(startGate, endGate));
            Thread.sleep(1000);
        }

        startGate.countDown();
        endGate.await();
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private class Runner implements Runnable {

        private CountDownLatch startLatch;
        private CountDownLatch endLatch;

        public Runner(CountDownLatch startLatch, CountDownLatch endLatch) {
            this.startLatch = startLatch;
            this.endLatch = endLatch;
        }

        @Override
        public void run() {
            try {
                printMessage("waiting for latch");
                startLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                endLatch.countDown();
            }
            printMessage("work done");
        }
    }

    public static void main(String[] args) {
        CountDownLatchExample countDownLatchExample = new CountDownLatchExample();
        countDownLatchExample.invoke();
    }
}