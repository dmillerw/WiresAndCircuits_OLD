package dmillerw.circuit.gate.arithmatic;

import dmillerw.circuit.api.gate.GateType;
import dmillerw.circuit.api.gate.IGate;
import dmillerw.circuit.api.tile.IGateSocket;
import dmillerw.circuit.api.value.ValueType;
import dmillerw.circuit.api.value.WrappedValue;

/**
 * @author dmillerw
 */
public class GateAdd implements IGate {

    @Override
    public GateType getType() {
        return GateType.ADD;
    }

    @Override
    public ValueType[] getInputTypes() {
        return new ValueType[] {ValueType.NUMBER, ValueType.NUMBER};
    }

    @Override
    public ValueType[] getOutputTypes() {
        return new ValueType[] {ValueType.NUMBER};
    }

    @Override
    public void tick(IGateSocket socket) {

    }

    @Override
    public void onInputUpdate(IGateSocket socket, int index) {
        socket.setOutput(0, WrappedValue.valueOf(socket.getInput(0).toNumber() + 3));
    }
}
