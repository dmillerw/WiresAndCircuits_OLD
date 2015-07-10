package dmillerw.circuit.api.tile;

import dmillerw.circuit.api.value.WrappedValue;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public interface IConnectable {

    World getWorld();
    ChunkCoordinates getCoordinates();

    int getInputCount();
    int getOutputCount();

    /**
     * Fires whenever a connection is established from this block to another
     */
    void onConnectionEstablished(ChunkCoordinates target, int selfOutput);

    /**
     * Fires whenever a connection is removed. Only fires on TARGET ports (ports receiving data from another)
     */
    void onConnectionRemoved(int input);

    /**
     * Updates the cached input value.
     */
    void setInput(int index, WrappedValue value);

    /**
     * Updates the cached output value. Value updates shouldn't send unless 'sendUpdate' is true
     */
    void setOutput(int index, WrappedValue value);

    /**
     * Retrieves the cached input value
     */
    WrappedValue getInput(int index);

    /**
     * Retrieves the cached output value
     */
    WrappedValue getOutput(int index);
}

