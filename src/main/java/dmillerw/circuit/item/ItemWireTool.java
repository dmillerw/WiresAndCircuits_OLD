package dmillerw.circuit.item;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.client.handler.ClientEventHandler;
import dmillerw.circuit.core.connection.Connection;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.network.packet.C01WireTool;
import dmillerw.circuit.util.NBTUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemWireTool extends Item {

    private static final class Target {

        ChunkCoordinates coordinates;
        int port;
    }

    public static void setTarget(ItemStack stack, ChunkCoordinates coordinates, int port) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
        }

        NBTTagCompound target = new NBTTagCompound();
        NBTUtil.writeChunkCoordinates(coordinates, target);
        tag.setTag("target", target);

        tag.setInteger("port", port);

        stack.setTagCompound(tag);
    }

    public static Target getTarget(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null || !tag.hasKey("target")) {
            return null;
        }

        ChunkCoordinates target = NBTUtil.readChunkCoordinates(tag.getCompoundTag("target"));
        int port = tag.getInteger("port");

        Target t = new Target();
        t.coordinates = target;
        t.port = port;
        return t;
    }

    public static void clearTarget(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            return;
        }

        tag.removeTag("target");
        tag.removeTag("port");
    }

    public ItemWireTool() {
        setMaxDamage(0);
        setMaxStackSize(1);

        setCreativeTab(CreativeTabs.tabRedstone);
    }

    public void onConnectionAdded(EntityPlayer entityPlayer, ItemStack itemStack, C01WireTool packet) {
        Target target = getTarget(itemStack);
        if (target == null) {
            setTarget(itemStack, packet.coordinates, packet.port);
        } else {
            Connection connection = new Connection(target.coordinates, packet.port, target.port);
            ConnectionHandler.INSTANCE.addConnection(entityPlayer.worldObj, packet.coordinates, connection);

            clearTarget(itemStack);
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && tile instanceof IConnectable) {
                C01WireTool packet = new C01WireTool(new ChunkCoordinates(x, y, z), ClientEventHandler.index);
                packet.sendToServer();
                return true;
            }
        }
        return false;
    }
}
