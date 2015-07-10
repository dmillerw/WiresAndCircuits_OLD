package dmillerw.circuit.tile;

import dmillerw.circuit.tile.core.TileCoreConnectable;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class TileScreen extends TileCoreConnectable {

    public ForgeDirection side = ForgeDirection.UNKNOWN;

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setByte("orientation", (byte) side.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        side = ForgeDirection.getOrientation(tag.getByte("orientation"));
    }

    @Override
    public void onNeighborUpdate(Block neighbor) {
        if (!worldObj.isRemote) {
            int ox = xCoord + side.getOpposite().offsetX;
            int oy = yCoord + side.getOpposite().offsetY;
            int oz = zCoord + side.getOpposite().offsetZ;

            Block block = worldObj.getBlock(ox, oy, oz);
            if (block.isAir(worldObj, ox, oy, oz) || !block.isSideSolid(worldObj, ox, oy, oz, side)) {
                worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, 0, 0);
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            }
        }
    }

    /* ICONNECTABLE */
    @Override
    public int getInputCount() {
        return 1;
    }

    @Override
    public int getOutputCount() {
        return 0;
    }
}
