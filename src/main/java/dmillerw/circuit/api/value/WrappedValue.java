package dmillerw.circuit.api.value;

/**
 * @author dmillerw
 */
public abstract class WrappedValue {

    public static WrappedBoolean valueOf(boolean value) {
        return new WrappedBoolean(value);
    }

    public static WrappedNumber valueOf(int value) {
        return new WrappedNumber(value);
    }

    public static WrappedNumber valueOf(double value) {
        return new WrappedNumber(value);
    }

    public static WrappedString valueOf(String value) {
        return new WrappedString(value);
    }

    public abstract boolean toBoolean();
    public abstract double toNumber();
    public abstract String toJString();

    public abstract boolean isBoolean();
    public abstract boolean isNumber();
    public abstract boolean isJString();
}
