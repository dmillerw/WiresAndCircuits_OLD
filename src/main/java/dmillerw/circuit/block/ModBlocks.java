package dmillerw.circuit.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.circuit.item.block.ItemBlockScreen;
import dmillerw.circuit.item.block.ItemBlockSocket;
import dmillerw.circuit.tile.TileDebug;
import dmillerw.circuit.tile.TileRedstoneAdapter;
import dmillerw.circuit.tile.TileScreen;
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
    public static Block screen;

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

        screen = new BlockScreen().setBlockName("screen");
        GameRegistry.registerBlock(screen, ItemBlockScreen.class, "screen");
        GameRegistry.registerTileEntity(TileScreen.class, prefix("screen"));
    }
}
