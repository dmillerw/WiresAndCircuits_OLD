package dmillerw.circuit.api.gate;

/**
 * @author dmillerw
 */
public enum GateType {

    ADD(GateCategory.ARITHMATIC);

    public final GateCategory category;
    private GateType(GateCategory category) {
        this.category = category;
    }
}
