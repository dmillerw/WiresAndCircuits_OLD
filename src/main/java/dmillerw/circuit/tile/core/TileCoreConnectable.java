package dmillerw.circuit.tile.core;

import dmillerw.circuit.api.tile.CachedState;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.network.packet.server.S01ValueUpdate;
import dmillerw.circuit.util.ConnectionUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

/**
 * @author dmillerw
 */
public abstract class TileCoreConnectable extends TileCore implements IConnectable {

    private CachedState cachedState = new CachedState(getInputCount(), getOutputCount());

    public final void refreshCache() {
        cachedState = new CachedState(getInputCount(), getOutputCount());
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        NBTTagCompound cache = new NBTTagCompound();

        final int inCount = getInputCount();
        final int outCount = getOutputCount();

        cache.setInteger("inCount", inCount);
        cache.setInteger("outCount", outCount);

        if (inCount > 0) {
            NBTTagList inputs = new NBTTagList();
            for (int i = 0; i < cachedState.inputs.length; i++) {
                NBTTagCompound index = new NBTTagCompound();
                index.setInteger("_index", i);
                index.setTag("_data", cachedState.inputs[i].getNBTTag());
                inputs.appendTag(index);
            }
            cache.setTag("inputs", inputs);
        }

        if (outCount > 0) {
            NBTTagList outputs = new NBTTagList();
            for (int i = 0; i < cachedState.outputs.length; i++) {
                NBTTagCompound index = new NBTTagCompound();
                index.setInteger("_index", i);
                index.setTag("_data", cachedState.outputs[i].getNBTTag());
                outputs.appendTag(index);
            }
            cache.setTag("outputs", outputs);
        }

        data.setTag("_CACHED_DATA", cache);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        NBTTagCompound cache = data.getCompoundTag("_CACHED_DATA");

        int inCount = cache.getInteger("inCount");
        int outCount = cache.getInteger("outCount");

        cachedState = new CachedState(inCount, outCount);

        if (inCount > 0 && cache.hasKey("inputs")) {
            NBTTagList inputs = cache.getTagList("inputs", TAG_COMPOUND);
            for (int i = 0; i < inputs.tagCount(); i++) {
                NBTTagCompound tag = inputs.getCompoundTagAt(i);

                int index = tag.getInteger("_index");
                WrappedValue value = WrappedValue.valueOf(tag.getTag("_data"));

                cachedState.inputs[index] = value;
            }
        }

        if (outCount > 0 && cache.hasKey("outputs")) {
            NBTTagList outputs = cache.getTagList("outputs", TAG_COMPOUND);
            for (int i = 0; i < outputs.tagCount(); i++) {
                NBTTagCompound tag = outputs.getCompoundTagAt(i);

                int index = tag.getInteger("_index");
                WrappedValue value = WrappedValue.valueOf(tag.getTag("_data"));

                cachedState.outputs[index] = value;
            }
        }
    }

    @Override
    public void onBlockBroken(Block block, int meta) {
        if (!worldObj.isRemote) {
            ConnectionHandler.INSTANCE.removeConnection(worldObj, new ChunkCoordinates(xCoord, yCoord, zCoord));
        }
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
    public void onConnectionEstablished(ChunkCoordinates target, int selfOutput) {
        ConnectionUtil.sendConnectableUpdate(this, selfOutput);
    }

    @Override
    public void onConnectionRemoved(int input) {
        setInput(input, WrappedValue.NULL);
    }

    @Override
    public void setInput(int index, WrappedValue value) {
        cachedState.inputs[index] = value;

        if (!worldObj.isRemote) {
            S01ValueUpdate.sendInputUpdate(this, index);
        }
    }

    @Override
    public void setOutput(int index, WrappedValue value) {
        cachedState.outputs[index] = value;

        if (!worldObj.isRemote) {
            S01ValueUpdate.sendOutputUpdate(this, index);
            ConnectionUtil.sendConnectableUpdate(this, index);
        }
    }

    @Override
    public WrappedValue getInput(int index) {
        return cachedState.inputs[index];
    }

    @Override
    public WrappedValue getOutput(int index) {
        return cachedState.outputs[index];
    }
}
