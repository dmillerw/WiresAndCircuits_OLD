package dmillerw.circuit.api.tile;

import dmillerw.circuit.api.gate.IGate;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public interface IGateSocket extends IConnectable {

    void setGate(IGate gate);
    NBTTagCompound getData();
}
