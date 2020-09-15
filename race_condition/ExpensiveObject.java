public class ExpensiveObject {

    public ExpensiveObject() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}