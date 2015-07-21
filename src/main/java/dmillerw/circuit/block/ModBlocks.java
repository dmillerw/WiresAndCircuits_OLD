package dmillerw.circuit.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.circuit.item.block.ItemBlockGateSocket;
import dmillerw.circuit.tile.tool.TileDebug;
import dmillerw.circuit.tile.tool.TileGateSocket;
import dmillerw.circuit.tile.tool.TileRedstoneAdapter;
import net.minecraft.block.Block;

import static dmillerw.circuit.WiresAndCircuits.prefix;

/**
 * @author dmillerw
 */
public class ModBlocks {

    public static Block redstoneAdapter;
    public static Block debug;
    public static Block gateSocket;

    public static void initialize() {
        redstoneAdapter = new BlockRedstoneAdapter().setBlockName("redstone_adapter");
        GameRegistry.registerBlock(redstoneAdapter, "redstone_adapter");
        GameRegistry.registerTileEntity(TileRedstoneAdapter.class, prefix("redstone_adapter"));

        debug = new BlockDebug().setBlockName("debug");
        GameRegistry.registerBlock(debug, "debug");
        GameRegistry.registerTileEntity(TileDebug.class, prefix("debug"));

        gateSocket = new BlockGateSocket().setBlockName("gateSocket");
        GameRegistry.registerBlock(gateSocket, ItemBlockGateSocket.class, "gateSocket");
        GameRegistry.registerTileEntity(TileGateSocket.class, prefix("gateSocket"));
    }
}
