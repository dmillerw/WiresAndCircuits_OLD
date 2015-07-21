package dmillerw.circuit.tile.tool;

import dmillerw.circuit.api.gate.Gate;
import dmillerw.circuit.api.gate.GateRegistry;
import dmillerw.circuit.tile.core.TileConnectable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class TileGateSocket extends TileConnectable {

    private static final String KEY_GATE = "gate";
    
    protected Gate gate;
    public final void setGate(Gate gate) {
        this.gate = gate;

        refreshStateHandler();
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (gate != null)
                gate.update(this);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (gate != null)
            tag.setString(KEY_GATE, GateRegistry.INSTANCE.getGateName(gate));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey(KEY_GATE))
            setGate(GateRegistry.INSTANCE.getGate(tag.getString(KEY_GATE)));
    }

    /* ICONNECTABLE */
    @Override
    public int getInputCount() {
        return gate == null ? 0 : gate.getInputCount();
    }

    @Override
    public int getOutputCount() {
        return gate == null ? 0 : gate.getOutputCount();
    }

    @Override
    public void onStateUpdate(int[] dirtyPorts) {
        if (gate != null)
            gate.onStateUpdate(this, dirtyPorts);
    }
}
