package dmillerw.circuit.api.value;

/**
 * @author dmillerw
 */
public class WrappedBoolean extends WrappedValue {

    private final boolean value;

    protected WrappedBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public boolean toBoolean() {
        return value;
    }

    @Override
    public double toNumber() {
        return value ? 1 : 0;
    }

    @Override
    public String toJString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isJString() {
        return false;
    }
}
