import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BadDateFormatter {
    
    private final String format = "dd-MM-yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
    private Storage storage = new Storage();
    private List<Date> dates;

    public void invoke() {
        initDates();
        printDates();
    }

    private void initDates() {
        dates = new ArrayList<>();
        long now = System.currentTimeMillis();
        long dayMilliseconds = 1000 * 60 * 60 * 24;

        for (int i = 0; i < 100; i++) {
            long mills = now + (i * dayMilliseconds);
            Date date = new Date(mills);
            dates.add(date);
        }
    }

    private void printDates() {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            final int index = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = formattedDate(index);
                    storage.storeDate(date);
                }
            });
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis() - start;
        storage.printMapSize();
        printMessage("time taken: " + end);
    }

    private String formattedDate(int index) {
        Date date = dates.get(index);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return dateFormat.format(date);
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        BadDateFormatter badDateFormatter = new BadDateFormatter();
        badDateFormatter.invoke();
    }
}