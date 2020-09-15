public class ImmutableEncoder {

    private volatile ImmutableObject cache = new ImmutableObject(0, 0);
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

    public void encode(int number) {
        // check it we already have encoded number in immutable object
        Integer encodedNumber = cache.getEncodedNumber(number);
        if (encodedNumber == null) {
            // if not, encode number and cache new immutable object
            encodedNumber = expensiveOperation(number);
            cache = new ImmutableObject(number, encodedNumber);
        }
        // no need to encode since we already have the number
        // synchronization is not needed because the state is immutable
        storage.store(number, encodedNumber);
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
        ImmutableEncoder immutableEncoder = new ImmutableEncoder();
        immutableEncoder.invoke();
    }
}