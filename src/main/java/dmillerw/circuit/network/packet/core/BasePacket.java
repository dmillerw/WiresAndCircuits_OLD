package dmillerw.circuit.network.packet.core;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.circuit.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author dmillerw
 */
public abstract class BasePacket implements IMessage {

    public abstract void toBytes(ByteBuf buf);
    public abstract void fromBytes(ByteBuf buf);

    public void sendToAll() {
        PacketHandler.INSTANCE.sendToAll(this);
    }

    public void sendTo(EntityPlayerMP player) {
        PacketHandler.INSTANCE.sendTo(this, player);
    }

    public void sendToAllAround(NetworkRegistry.TargetPoint point) {
        PacketHandler.INSTANCE.sendToAllAround(this, point);
    }

    public void sendToDimension(int dimensionId) {
        PacketHandler.INSTANCE.sendToDimension(this, dimensionId);
    }

    @SideOnly(Side.CLIENT)
    public void sendToServer() {
        PacketHandler.INSTANCE.sendToServer(this);
    }
}
