package dmillerw.circuit.tile.core;

import dmillerw.circuit.api.tile.CachedState;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.core.connection.ConnectionHandler;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChunkCoordinates;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

/**
 * @author dmillerw
 */
public abstract class TileCoreConnectable extends TileCore implements IConnectable {

    //TODO Re-vamp this cached state idea
    //TODO Serialize with tile?

    private CachedState cachedState = new CachedState(getInputTypes().length, getOutputTypes().length);

    public final void refreshCache() {
        cachedState = new CachedState(getInputTypes().length, getOutputTypes().length);
    }

    /* NETWORKING */
    @Override
    public Packet getDescriptionPacket() {
        // Sync normal data AND cache
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);


        NBTTagCompound cache = new NBTTagCompound();

        final int inCount = getInputTypes().length;
        final int outCount = getOutputTypes().length;

        cache.setInteger("inCount", inCount);
        cache.setInteger("outCount", outCount);

        if (inCount > 0) {
            NBTTagList inputs = new NBTTagList();
            for (int i=0; i<cachedState.inputs.length; i++) {
                NBTTagCompound index = new NBTTagCompound();
                index.setInteger("_index", i);
                index.setTag("_data", cachedState.inputs[i].getNBTTag());
                inputs.appendTag(index);
            }
            cache.setTag("inputs", inputs);
        }

        if (outCount > 0) {
            NBTTagList outputs = new NBTTagList();
            for (int i=0; i<cachedState.outputs.length; i++) {
                NBTTagCompound index = new NBTTagCompound();
                index.setInteger("_index", i);
                index.setTag("_data", cachedState.outputs[i].getNBTTag());
                outputs.appendTag(index);
            }
            cache.setTag("outputs", outputs);
        }

        data.setTag("_CACHED_DATA", cache);

        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound data = pkt.func_148857_g();

        // Vanilla handling first
        readFromNBT(data);

        // Then read our cache data
        NBTTagCompound cache = data.getCompoundTag("_CACHED_DATA");

        int inCount = cache.getInteger("inCount");
        int outCount = cache.getInteger("outCount");

        cachedState = new CachedState(inCount, outCount);

        if (inCount > 0 && cache.hasKey("inputs")) {
            NBTTagList inputs = cache.getTagList("inputs", TAG_COMPOUND);
            for (int i=0; i<inputs.tagCount(); i++) {
                NBTTagCompound tag = inputs.getCompoundTagAt(i);

                int index = tag.getInteger("_index");
                WrappedValue value = WrappedValue.valueOf(tag.getTag("_data"));

                cachedState.inputs[index] = value;
            }
        }

        if (outCount > 0 && cache.hasKey("outputs")) {
            NBTTagList outputs = cache.getTagList("outputs", TAG_COMPOUND);
            for (int i=0; i<outputs.tagCount(); i++) {
                NBTTagCompound tag = outputs.getCompoundTagAt(i);

                int index = tag.getInteger("_index");
                WrappedValue value = WrappedValue.valueOf(tag.getTag("_data"));

                cachedState.outputs[index] = value;
            }
        }

        markForRenderUpdate();
    }

    @Override
    public void onBlockBroken(Block block, int meta) {
        if (!worldObj.isRemote) {
            ConnectionHandler.INSTANCE.removeConnection(worldObj, new ChunkCoordinates(xCoord, yCoord, zCoord));
        }
    }

    /* ICONNECTABLE */
    @Override
    public void onInputUpdate(int index, WrappedValue value) {
        cachedState.inputs[index] = value;
        markForUpdate();
    }

    @Override
    public WrappedValue getInput(int index) {
        return cachedState.inputs[index];
    }

    @Override
    public WrappedValue getOutput(int index) {
        return cachedState.outputs[index];
    }

    @Override
    public void setOutput(int index, WrappedValue value) {
        cachedState.outputs[index] = value;
        markForUpdate();
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
