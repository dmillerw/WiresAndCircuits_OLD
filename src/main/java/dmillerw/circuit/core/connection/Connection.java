package dmillerw.circuit.core.connection;

import dmillerw.circuit.util.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

/**
 * @author dmillerw
 */
public class Connection {

    public static Connection readFromNBT(NBTTagCompound tag) {
        NBTTagCompound target = tag.getCompoundTag("target");
        return new Connection(NBTUtil.readChunkCoordinates(target), tag.getInteger("selfOut"), tag.getInteger("targetIn"));
    }

    public final ChunkCoordinates target;

    public final int selfOutputPort;
    public final int targetInputPort;

    public Connection(ChunkCoordinates target, int selfOutputPort, int targetInputPort) {
        this.target = target;
        this.selfOutputPort = selfOutputPort;
        this.targetInputPort = targetInputPort;
    }

    public void writeToNBT(NBTTagCompound tag) {
        NBTTagCompound targetTag = new NBTTagCompound();
        NBTUtil.writeChunkCoordinates(target, targetTag);

        tag.setTag("target", targetTag);
        tag.setInteger("selfOut", selfOutputPort);
        tag.setInteger("targetIn", targetInputPort);
    }

    @Override
    public String toString() {
        return "[connection: {target: " + target + ", selfOutput: " + selfOutputPort + ", targetInput: " + targetInputPort + "}]";
    }
}
