package dmillerw.circuit.api.gate;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.util.APIHelper;

/**
 * @author dmillerw
 */
public abstract class Gate {

    public static final String CATEGORY_ARITHMATIC = "arithmatic";
    public static final String CATEGORY_COMPARISON = "comparison";

    protected final String category;
    protected final Tier tier;
    protected final int inputCount;
    protected final int outputCount;

    public Gate(String category, Tier tier, int inputCount, int outputCount) {
        this.category = category;
        this.tier = tier;
        this.inputCount = inputCount;
        this.outputCount = outputCount;
    }

    public final Tier getTier() {
        return tier;
    }

    public final String getCategory() {
        return category;
    }

    public final int getInputCount() {
        return inputCount;
    }

    public final int getOutputCount() {
        return outputCount;
    }

    public String getPortLabel(boolean input, int index) {
        return APIHelper.getLetter(index);
    }

    public abstract void update(IConnectable connectable);
    public abstract void onStateUpdate(IConnectable connectable, int[] dirtyPorts);
}
