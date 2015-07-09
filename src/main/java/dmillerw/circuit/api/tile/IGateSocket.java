package dmillerw.circuit.api.tile;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public interface IGateSocket extends IConnectable {

    NBTTagCompound getData();
}
