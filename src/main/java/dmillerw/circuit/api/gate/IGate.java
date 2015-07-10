package dmillerw.circuit.api.gate;

import dmillerw.circuit.api.tile.IGateSocket;

/**
 * @author dmillerw
 */
public interface IGate {

    GateType getType();

    int getInputCount();
    int getOutputCount();

    void tick(IGateSocket socket);

    /**
     * Simply called to inform the Gate that the owning socket has been updated in some way
     */
    void onInputUpdate(IGateSocket socket, int index);
}
