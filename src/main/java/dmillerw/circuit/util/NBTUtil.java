package dmillerw.circuit.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

/**
 * @author dmillerw
 */
public class NBTUtil {

    public static void writeChunkCoordinates(ChunkCoordinates coordinates, NBTTagCompound tag) {
        tag.setInteger("x", coordinates.posX);
        tag.setInteger("y", coordinates.posY);
        tag.setInteger("z", coordinates.posZ);
    }

    public static ChunkCoordinates readChunkCoordinates(NBTTagCompound tag) {
        return new ChunkCoordinates(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
    }
}
