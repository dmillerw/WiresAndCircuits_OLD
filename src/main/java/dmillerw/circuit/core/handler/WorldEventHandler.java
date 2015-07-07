package dmillerw.circuit.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.circuit.core.connection.ConnectionWorldData;
import net.minecraftforge.event.world.WorldEvent;

/**
 * @author dmillerw
 */
public class WorldEventHandler {

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        ConnectionWorldData.load(event.world);
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        ConnectionWorldData.unload(event.world);
    }
}
