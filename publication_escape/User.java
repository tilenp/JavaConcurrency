public class User {

    public void registerListener(Listener listener) {
        int value = listener.getValue();

        System.out.println("value: " + value);
    }
}