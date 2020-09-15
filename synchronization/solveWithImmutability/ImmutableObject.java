public class ImmutableObject {

    private final int number;
    private final int encodedNumber;

    public ImmutableObject(int number, int encodedNumber) {
        this.number = number;
        this.encodedNumber = encodedNumber;
        }

    public Integer getEncodedNumber(int number) {
        return this.number == number ? encodedNumber : null;
    }
}