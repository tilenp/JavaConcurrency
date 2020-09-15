import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample {
    
    /**
     * threads have tasks that take different amounts of time but CyclicBarrier
     * synchronizes them to start a new task together
     **/
 
    private int numberOfParties = 3;
    private int numberOfThreads = 3;
    private Random random = new Random();

    private void invoke() {
        CyclicBarrier barrier = new CyclicBarrier(numberOfParties);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(new Runner(barrier));
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       printMessage("all done");
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private class Runner implements Runnable {

        private CyclicBarrier barrier;

        public Runner(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {

                int timeToWait = random.nextInt(1000);
                printMessage("round " + i + " " + Thread.currentThread().getName() + " will sleep for " + timeToWait);
                try {
                    Thread.sleep(timeToWait);
                    barrier.await();
                } catch (BrokenBarrierException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrierExample cyclicBarrierExample = new CyclicBarrierExample();
        cyclicBarrierExample.invoke();
    }
}