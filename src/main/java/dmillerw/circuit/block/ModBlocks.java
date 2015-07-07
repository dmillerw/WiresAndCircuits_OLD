package dmillerw.circuit.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.circuit.item.ItemBlockSocket;
import dmillerw.circuit.tile.TileDebug;
import dmillerw.circuit.tile.TileRedstoneAdapter;
import dmillerw.circuit.tile.TileSocket;
import net.minecraft.block.Block;

import static dmillerw.circuit.WiresAndCircuits.prefix;

/**
 * @author dmillerw
 */
public class ModBlocks {

    public static Block debug;
    public static Block redstoneAdapter;
    public static Block socket;

    public static void initialize() {
        debug = new BlockDebug().setBlockName("debug");
        GameRegistry.registerBlock(debug, "debug");
        GameRegistry.registerTileEntity(TileDebug.class, prefix("debug"));

        redstoneAdapter = new BlockRedstoneAdapter().setBlockName("redstoneAdapter");
        GameRegistry.registerBlock(redstoneAdapter, "redstoneAdapter");
        GameRegistry.registerTileEntity(TileRedstoneAdapter.class, prefix("redstoneAdapter"));

        socket = new BlockSocket().setBlockName("socket");
        GameRegistry.registerBlock(socket, ItemBlockSocket.class, "socket");
        GameRegistry.registerTileEntity(TileSocket.class, prefix("socket"));
    }
}
