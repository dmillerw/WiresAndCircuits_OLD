package dmillerw.circuit.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.circuit.WiresAndCircuits;
import dmillerw.circuit.network.packet.client.C01WireTool;
import dmillerw.circuit.network.packet.core.BasePacket;
import dmillerw.circuit.network.packet.server.S01ValueUpdate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(WiresAndCircuits.ID);

    public static void initialize() {
        // CLIENT -> SERVER
        INSTANCE.registerMessage(C01WireTool.Handler.class, C01WireTool.class, -1, Side.SERVER);

        // SERVER -> CLIENT
        INSTANCE.registerMessage(S01ValueUpdate.Handler.class, S01ValueUpdate.class, 1, Side.CLIENT);
    }

    public static void sendToAllWatching(TileEntity tile, BasePacket packet) {
        sendToAllWatching(tile.getWorldObj().getChunkFromBlockCoords(tile.xCoord, tile.zCoord), packet);
    }

    public static void sendToAllWatching(Chunk chunk, BasePacket packet) {
        final ChunkCoordIntPair coord = new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);

        WorldServer world = (WorldServer) chunk.worldObj;
        PlayerManager manager = world.getPlayerManager();

        for (int i=0; i<manager.playerInstanceList.size(); i++) {
            PlayerManager.PlayerInstance instance = (PlayerManager.PlayerInstance) manager.playerInstanceList.get(i);

            for (int j = 0; j < instance.playersWatchingChunk.size(); ++j) {
                EntityPlayerMP player = (EntityPlayerMP) instance.playersWatchingChunk.get(j);

                if (!player.loadedChunks.contains(coord)) {
                    packet.sendTo(player);
                }
            }
        }
    }
}
