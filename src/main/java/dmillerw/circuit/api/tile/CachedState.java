package dmillerw.circuit.api.tile;

import dmillerw.circuit.api.value.WrappedNull;
import dmillerw.circuit.api.value.WrappedValue;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class CachedState {

    public WrappedValue[] inputs;
    public WrappedValue[] outputs;

    public CachedState(int inLength, int outLength) {
        this.inputs = new WrappedValue[inLength];
        this.outputs = new WrappedValue[outLength];

        for (int i=0; i<inputs.length; i++) {
            inputs[i] = new WrappedNull();
        }

        for (int i=0; i<outputs.length; i++) {
            outputs[i] = new WrappedNull();
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {

    }

    public void readFromNBT(NBTTagCompound nbt) {

    }
}
