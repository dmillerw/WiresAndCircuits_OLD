package dmillerw.circuit.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.circuit.WiresAndCircuits;
import dmillerw.circuit.network.packet.client.C01WireTool;
import dmillerw.circuit.network.packet.server.S01SetConnections;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(WiresAndCircuits.ID);

    public static void initialize() {
        // CLIENT -> SERVER
        INSTANCE.registerMessage(C01WireTool.Handler.class, C01WireTool.class, -1, Side.SERVER);

        // SERVER -> CLIENT
        INSTANCE.registerMessage(S01SetConnections.Handler.class, S01SetConnections.class, 1, Side.CLIENT);
    }
}
