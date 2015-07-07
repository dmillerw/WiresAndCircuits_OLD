package dmillerw.circuit.tile;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.ValueType;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.tile.core.TileCoreConnectable;
import net.minecraft.block.Block;

/**
 * @author dmillerw
 */
public class TileRedstoneAdapter extends TileCoreConnectable implements IConnectable {

    private int lastRedstone = -1;

    @Override
    public void onNeighborUpdate(Block neighbor) {
        if (!worldObj.isRemote) {
            int redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ? 15 : 0;
            if (lastRedstone != redstone) {
                lastRedstone = redstone;
                setOutput(0, WrappedValue.valueOf(lastRedstone));
                sendPortUpdate(0);
            }
        }
    }

    /* ICONNECTABLE */
    @Override
    public ValueType[] getInputTypes() {
        return new ValueType[0];
    }

    @Override
    public ValueType[] getOutputTypes() {
        return new ValueType[] {ValueType.NUMBER};
    }
}
