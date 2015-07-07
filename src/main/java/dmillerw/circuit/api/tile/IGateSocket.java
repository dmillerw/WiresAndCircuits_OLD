package dmillerw.circuit.api.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public interface IGateSocket extends IConnectable {

    World getWorld();

    int getX();
    int getY();
    int getZ();

    NBTTagCompound getData();
}
