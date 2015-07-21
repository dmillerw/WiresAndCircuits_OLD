package dmillerw.circuit.api.tile;

import dmillerw.circuit.api.value.WrappedValue;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public interface IStateHandler {

    WrappedValue getInput(int index);
    WrappedValue getOutput(int index);

    void setInput(int index, WrappedValue value);
    void setOutput(int index, WrappedValue value);

    void writeToNBT(NBTTagCompound tag);
    void readFromNBT(NBTTagCompound tag);

    void markDirty();
}
