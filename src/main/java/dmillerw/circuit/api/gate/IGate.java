package dmillerw.circuit.api.gate;

import dmillerw.circuit.api.tile.IGateSocket;
import dmillerw.circuit.api.value.ValueType;

/**
 * @author dmillerw
 */
public interface IGate {

    GateType getType();

    ValueType[] getInputTypes();
    ValueType[] getOutputTypes();

    void tick(IGateSocket socket);

    /**
     * Simply called to inform the Gate that the owning socket has been updated in some way
     */
    void onInputUpdate(IGateSocket socket, int index);
}
