package dmillerw.circuit.core.connection;

import com.google.common.collect.Sets;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.tile.IStateHandler;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.network.packet.server.S01ValueUpdate;
import dmillerw.circuit.util.ConnectionUtil;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Set;

import static dmillerw.circuit.api.value.WrappedValue.NULL;

/**
 * @author dmillerw
 */
public class StateHandler implements IStateHandler {

    protected final IConnectable owner;
    private Set<Integer> dirtyPorts = Sets.newHashSet();

    private WrappedValue[] inputs;
    private WrappedValue[] outputs;

    public StateHandler(IConnectable owner) {
        this.owner = owner;

        this.inputs = new WrappedValue[owner.getInputCount()];
        for (int i=0; i<inputs.length; i++) {
            inputs[i] = NULL;
        }

        this.outputs = new WrappedValue[owner.getOutputCount()];
        for (int i=0; i<outputs.length; i++) {
            outputs[i] = NULL;
        }
    }

    private boolean valid(boolean input, int index) {
        return input ? (inputs != null &&  index < inputs.length) : (outputs != null && index < outputs.length);
    }

    @Override
    public final WrappedValue getInput(int index) {
        return valid(true, index) ? inputs[index] : NULL;
    }

    @Override
    public final WrappedValue getOutput(int index) {
        return valid(false, index) ? outputs[index] : NULL;
    }

    @Override
    public final void setInput(int index, WrappedValue value) {
        if (valid(true, index)) {
            inputs[index] = value;

            dirtyPorts.add(index);

            if (!owner.getWorld().isRemote) {
                S01ValueUpdate.sendInputUpdate(owner, index);
            }
        }
    }

    @Override
    public final void setOutput(int index, WrappedValue value) {
        if (valid(false, index)) {
            outputs[index] = value;

            if (!owner.getWorld().isRemote) {
                S01ValueUpdate.sendOutputUpdate(owner, index);
                ConnectionUtil.sendConnectableUpdate(owner, index);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {

    }

    @Override
    public void markDirty() {
        owner.onStateUpdate(ArrayUtils.toPrimitive(dirtyPorts.toArray(new Integer[dirtyPorts.size()])));
        dirtyPorts.clear();
    }
}
