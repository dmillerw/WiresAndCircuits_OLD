package dmillerw.circuit.item.block;

import dmillerw.circuit.api.gate.Gate;
import dmillerw.circuit.api.gate.GateRegistry;
import dmillerw.circuit.tile.tool.TileGateSocket;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemBlockGateSocket extends ItemBlock {

    public ItemBlockGateSocket(Block block) {
        super(block);

        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(1);

        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean debug) {
        String type = itemStack.getTagCompound().getString("type");
        Gate gate = GateRegistry.INSTANCE.getGate(type);
        list.add("Gate - " + gate.getCategory() + ": " + type);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (String string : GateRegistry.INSTANCE.getAllGates()) {
            ItemStack stack = new ItemStack(this, 1, 0);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("type", string);
            stack.setTagCompound(tag);
            list.add(stack);
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean result = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (result) {
            TileGateSocket tile = (TileGateSocket) world.getTileEntity(x, y, z);
            tile.setGate(GateRegistry.INSTANCE.getGate(stack.getTagCompound().getString("type")));
            tile.markForUpdate();
        }
        return true;
    }
}
