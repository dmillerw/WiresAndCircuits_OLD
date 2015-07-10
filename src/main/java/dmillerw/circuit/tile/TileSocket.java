package dmillerw.circuit.tile;

import dmillerw.circuit.api.gate.IGate;
import dmillerw.circuit.api.tile.IGateSocket;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.tile.core.TileCoreConnectable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class TileSocket extends TileCoreConnectable implements IGateSocket {

    private IGate gateInstance;

    /* ICONNECTABLE */
    @Override
    public int getInputCount() {
        return gateInstance == null ? 0 : gateInstance.getInputCount();
    }

    @Override
    public int getOutputCount() {
        return gateInstance == null ? 0 : gateInstance.getOutputCount();
    }

    @Override
    public void setGate(IGate gate) {
        gateInstance = gate;
        refreshCache();
    }

    /* IGATESOCKET */
    @Override
    public NBTTagCompound getData() {
        return null;
    }

    /* ICONNECTABLE */
    @Override
    public void setInput(int index, WrappedValue value) {
        super.setInput(index, value);

        if (!worldObj.isRemote) {
            if (gateInstance != null) {
                gateInstance.onInputUpdate(this, index);
            }
        }
    }
}
