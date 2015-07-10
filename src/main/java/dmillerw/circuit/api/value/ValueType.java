package dmillerw.circuit.api.value;

/**
 * @author dmillerw
 */
public enum ValueType {

    BOOLEAN(WrappedValue.valueOf(false)),
    NUMBER(WrappedValue.valueOf(0)),
    STRING(WrappedValue.valueOf("")),
    NULL(WrappedValue.NULL);

    public WrappedValue zero;
    private ValueType(WrappedValue zero) {
        this.zero = zero;
    }

    public WrappedValue cast(WrappedValue value) {
        return WrappedValue.cast(this, value);
    }
}
