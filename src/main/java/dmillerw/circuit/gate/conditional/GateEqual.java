package dmillerw.circuit.gate.conditional;

import dmillerw.circuit.api.gate.GateType;
import dmillerw.circuit.api.gate.IGate;
import dmillerw.circuit.api.tile.IGateSocket;
import dmillerw.circuit.api.value.WrappedValue;

/**
 * @author dmillerw
 */
public class GateEqual implements IGate {

    @Override
    public GateType getType() {
        return GateType.EQUAL;
    }

    @Override
    public int getInputCount() {
        return 2;
    }

    @Override
    public int getOutputCount() {
        return 1;
    }

    @Override
    public void tick(IGateSocket socket) {

    }

    @Override
    public void onInputUpdate(IGateSocket socket, int index) {
        socket.setOutput(0, WrappedValue.valueOf(socket.getInput(0).equals(socket.getInput(1))));
    }
}
