package dmillerw.circuit.block;

import dmillerw.circuit.tile.core.TileCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public abstract class BlockTileCore extends BlockContainer {

    public BlockTileCore(Material material) {
        super(material);

        setHardness(2F);
        setResistance(2F);

        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileCore tileCore = (TileCore) world.getTileEntity(x, y, z);
        if (tileCore != null) {
            tileCore.onNeighborUpdate(block);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileCore tileCore = (TileCore) world.getTileEntity(x, y, z);
        if (tileCore != null) {
            tileCore.onBlockBroken(block, meta);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return getTileEntity(metadata);
    }

    public abstract TileCore getTileEntity(int metadata);
}
