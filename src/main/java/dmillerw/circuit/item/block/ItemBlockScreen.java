package dmillerw.circuit.item.block;

import dmillerw.circuit.tile.TileScreen;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class ItemBlockScreen extends ItemBlock {

    public ItemBlockScreen(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        ForgeDirection fside = ForgeDirection.getOrientation(side);
        int ox = x + fside.getOpposite().offsetX;
        int oy = y + fside.getOpposite().offsetY;
        int oz = z + fside.getOpposite().offsetZ;

        if (!world.getBlock(ox, oy, oz).isSideSolid(world, ox, oy, oz, ForgeDirection.getOrientation(side)))
            return false;

        boolean result = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (result) {
            TileScreen tile = (TileScreen) world.getTileEntity(x, y, z);
            tile.side = ForgeDirection.getOrientation(side);
            tile.markForUpdate();
        }
        return true;
    }
}
