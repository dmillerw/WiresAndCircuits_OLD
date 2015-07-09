package dmillerw.circuit.network.packet.client;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.circuit.item.ItemWireTool;
import dmillerw.circuit.item.ModItems;
import dmillerw.circuit.network.packet.core.BaseHandler;
import dmillerw.circuit.network.packet.core.BasePacket;
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

    public static class Handler extends BaseHandler<C01WireTool> {

        @Override
        public void handle(C01WireTool message, MessageContext context) {
            ItemStack held = context.getServerHandler().playerEntity.getHeldItem();
            if (held == null || held.getItem() != ModItems.wireTool) {
                // Throw error
                return;
            }

            ((ItemWireTool)held.getItem()).onConnectionAdded(context.getServerHandler().playerEntity, held, message);
        }
    }
}
