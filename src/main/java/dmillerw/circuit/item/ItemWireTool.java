package dmillerw.circuit.item;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.core.connection.Connection;
import dmillerw.circuit.core.connection.ConnectionHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemWireTool extends Item {

    // SO TEMPORARY
    public ChunkCoordinates connection = null;

    public ItemWireTool() {
        setMaxDamage(0);
        setMaxStackSize(1);

        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity != null && tileEntity instanceof IConnectable) {
                if (connection == null) {
                    connection = new ChunkCoordinates(x, y, z);
                } else {
                    ConnectionHandler.INSTANCE.addConnection(world, new ChunkCoordinates(x, y, z), new Connection(connection, 0, 0));
                }
                return true;
            }
        }
        return false;
    }
}
