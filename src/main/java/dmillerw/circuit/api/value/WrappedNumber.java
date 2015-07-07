package dmillerw.circuit.api.value;

/**
 * @author dmillerw
 */
public class WrappedNumber extends WrappedValue {

    private final double value;

    protected WrappedNumber(double value) {
        this.value = value;
    }

    @Override
    public boolean toBoolean() {
        return false;
    }

    @Override
    public double toNumber() {
        return value;
    }

    @Override
    public String toJString() {
        return Double.toString(value);
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isJString() {
        return false;
    }
}
