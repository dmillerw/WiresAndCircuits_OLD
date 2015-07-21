package dmillerw.circuit.tile.core;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.tile.IStateHandler;
import dmillerw.circuit.core.connection.StateHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public abstract class TileConnectable extends TileCore implements IConnectable {

    private StateHandler stateHandler;

    public TileConnectable() {
        refreshStateHandler();
    }

    protected final void refreshStateHandler() {
        stateHandler = new StateHandler(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        stateHandler.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        stateHandler.writeToNBT(tag);
    }

    /* ICONNECTABLE */
    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public ChunkCoordinates getCoordinates() {
        return new ChunkCoordinates(xCoord, yCoord, zCoord);
    }

    @Override
    public abstract int getInputCount();
    @Override
    public abstract int getOutputCount();

    @Override
    public IStateHandler getStateHandler() {
        return stateHandler;
    }
}
