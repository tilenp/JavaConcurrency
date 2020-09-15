public class SafeObject {

    public int value;
    private final Listener listener;

    private SafeObject() {
        listener = new Listener() {
            @Override
            public int getValue() {
                return value;
            }
        };
        value = 42;
    }

    // use a factory method to register a listener
    public static SafeObject newInstance(User user) {
        SafeObject safeObject = new SafeObject();
        user.registerListener(safeObject.listener);
        return safeObject;
    }
}