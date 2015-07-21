package dmillerw.circuit.gate.arithmatic;

import dmillerw.circuit.api.gate.Gate;
import dmillerw.circuit.api.gate.Tier;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;

/**
 * @author dmillerw
 */
public class GateAdd extends Gate {

    public GateAdd() {
        super(CATEGORY_ARITHMATIC, Tier.REDSTONE, 8, 1);
    }

    @Override
    public void update(IConnectable connectable) {

    }

    @Override
    public void onStateUpdate(IConnectable connectable, int[] dirtyPorts) {
        double amt = 0;
        for (int i=0; i<8; i++) {
            amt += connectable.getStateHandler().getInput(i).toNumber();
        }
        connectable.getStateHandler().setOutput(0, WrappedValue.valueOf(amt));
    }
}
