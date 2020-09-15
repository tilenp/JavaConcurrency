public class NonSafeObject {

    public int value;

    // never start a thread or register a listener in the constructor
    public NonSafeObject(User user) {
        user.registerListener(new Listener() {
            @Override
            public int getValue() {
               return value;
            }
        });
        value = 42;
    }
}