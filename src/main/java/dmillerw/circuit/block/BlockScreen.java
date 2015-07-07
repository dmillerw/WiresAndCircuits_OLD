package dmillerw.circuit.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.circuit.tile.TileScreen;
import dmillerw.circuit.tile.core.TileCore;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockScreen extends BlockTileCore {

    public BlockScreen() {
        super(Material.iron);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        TileScreen tile = (TileScreen) world.getTileEntity(x, y, z);
        if (tile != null && tile.side != null) {
            switch (tile.side) {
                case DOWN:
                    setBlockBounds(0.1F, 0.9F, 0.1F, 0.9F, 1F, 0.9F);
                    break;
                case UP:
                    setBlockBounds(0.1F, 0F, 0.1F, 0.9F, 0.1F, 0.9F);
                    break;
                case NORTH:
                    setBlockBounds(0.1F, 0.1F, 1F, 0.9F, 0.9F, 0.9F);
                    break;
                case SOUTH:
                    setBlockBounds(0.1F, 0.1F, 0F, 0.9F, 0.9F, 0.1F);
                    break;
                case EAST:
                    setBlockBounds(0F, 0.1F, 0.1F, 0.1F, 0.9F, 0.9F);
                    break;
                case WEST:
                    setBlockBounds(1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
                    break;
                default:
                    break;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public TileCore getTileEntity(int metadata) {
        return new TileScreen();
    }
}
