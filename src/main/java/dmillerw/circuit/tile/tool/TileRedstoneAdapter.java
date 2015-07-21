package dmillerw.circuit.tile.tool;

import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.tile.core.TileConnectable;
import net.minecraft.block.Block;

/**
 * @author dmillerw
 */
public class TileRedstoneAdapter extends TileConnectable {

    private int lastRedstone = -1;

    @Override
    public void onNeighborUpdate(Block neighbor) {
        if (!worldObj.isRemote) {
            int redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ? 15 : 0;
            if (redstone != lastRedstone) {
                lastRedstone = redstone;

                System.out.println("UPDATE");

                getStateHandler().setOutput(0, WrappedValue.valueOf(lastRedstone));
            }
        }
    }

    /* ICONNECTABLE */
    @Override
    public int getInputCount() {
        return 0;
    }

    @Override
    public int getOutputCount() {
        return 1;
    }

    @Override
    public void onStateUpdate(int[] dirtyPorts) {

    }
}
