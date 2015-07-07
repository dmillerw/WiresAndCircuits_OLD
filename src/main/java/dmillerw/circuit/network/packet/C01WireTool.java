package dmillerw.circuit.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.circuit.item.ItemWireTool;
import dmillerw.circuit.item.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

/**
 * @author dmillerw
 */
public class C01WireTool extends BasePacket {

    public ChunkCoordinates coordinates;
    public int port;

    public C01WireTool() {

    }

    public C01WireTool(ChunkCoordinates coordinates, int port) {
        this.coordinates = coordinates;
        this.port = port;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(coordinates.posX);
        buf.writeInt(coordinates.posY);
        buf.writeInt(coordinates.posZ);
        buf.writeInt(port);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        coordinates = new ChunkCoordinates(buf.readInt(), buf.readInt(), buf.readInt());
        port = buf.readInt();
    }

    public static class Handler implements IMessageHandler<C01WireTool, IMessage> {

        @Override
        public IMessage onMessage(C01WireTool message, MessageContext ctx) {
            ItemStack held = ctx.getServerHandler().playerEntity.getHeldItem();
            if (held == null || held.getItem() != ModItems.wireTool) {
                // Throw error
                return null;
            }

            ((ItemWireTool)held.getItem()).onConnectionAdded(ctx.getServerHandler().playerEntity, held, message);

            return null;
        }
    }
}
