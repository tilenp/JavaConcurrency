public class StarterClass {

    public static void main(String[] args) {
        User user = new User();
        NonSafeObject nonSafeObject = new NonSafeObject(user);
        SafeObject safeObject = SafeObject.newInstance(user);
    }
    
}