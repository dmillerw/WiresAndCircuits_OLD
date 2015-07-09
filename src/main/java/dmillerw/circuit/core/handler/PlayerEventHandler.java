package dmillerw.circuit.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import dmillerw.circuit.network.packet.server.S01SetConnections;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author dmillerw
 */
public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        S01SetConnections.get(event.player.worldObj.provider.dimensionId).sendTo((EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        S01SetConnections.get(event.toDim).sendTo((EntityPlayerMP) event.player);
    }
}
