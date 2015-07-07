package dmillerw.circuit.tile.core;

import dmillerw.circuit.api.tile.CachedState;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.core.connection.ConnectionHandler;
import net.minecraft.util.ChunkCoordinates;

/**
 * @author dmillerw
 */
public abstract class TileCoreConnectable extends TileCore implements IConnectable {

    private CachedState cachedState = new CachedState(getInputTypes().length, getOutputTypes().length);

    public final void refreshCache() {
        cachedState = new CachedState(getInputTypes().length, getOutputTypes().length);
    }

    @Override
    public void onInputUpdate(int index, WrappedValue value) {
        cachedState.inputs[index] = value;
    }

    @Override
    public WrappedValue getInput(int index) {
        return cachedState.inputs[index];
    }

    @Override
    public void setOutput(int index, WrappedValue value) {
        cachedState.outputs[index] = value;
    }

    @Override
    public void sendPortUpdate(int index) {
        if (index == -1) {
            for (int i=0; i<getOutputTypes().length; i++) {
                ConnectionHandler.INSTANCE.queueUpdate(worldObj, new ChunkCoordinates(xCoord, yCoord, zCoord), i, cachedState.outputs[i]);
            }
        } else {
            ConnectionHandler.INSTANCE.queueUpdate(worldObj, new ChunkCoordinates(xCoord, yCoord, zCoord), index, cachedState.outputs[index]);
        }
    }
}
