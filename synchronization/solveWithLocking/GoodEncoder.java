
public class GoodEncoder {

    private int lastNumber;
    private int encodedNumber;
    private Storage storage = new Storage();

    public void invoke() {
        int[] numbers1 = {1,1,1,1,1,1};
        int[] numbers2 = {1,2,1,2,1,2};

        Thread thread1 = new Thread(new Runner(numbers1));
        Thread thread2 = new Thread(new Runner(numbers2));

        long start = System.currentTimeMillis();
        thread2.start();
        thread1.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        storage.printMap();
        printMessage("time taken = " + (end - start));
    }

    /** apply synchronization only when checking variables or when variables are updated **/
    public void encode(int number) {
        Integer encoded = null;
        synchronized (this) {
            if (number == lastNumber) {
                encoded = encodedNumber;
            }
        }

        if (encoded == null) {
            encoded = expensiveOperation(number);
            synchronized (this) {
                lastNumber = number;
                encodedNumber = encoded;
            }
        }

        storage.store(number, encoded);
    }

    private int expensiveOperation(int number) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number * 10;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private class Runner implements Runnable {

        private final int[] numbers;

        Runner(int[] numbers) {
            this.numbers = numbers;
        }

        @Override
        public void run() {
            for (int number : numbers) {
                encode(number);
            }
        }
    }
    
    public static void main(String[] args) {
        GoodEncoder goodEncoder = new GoodEncoder();
        goodEncoder.invoke();
    }
}