package dmillerw.circuit.network.packet.server;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.ValueType;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.network.PacketHandler;
import dmillerw.circuit.network.packet.core.BaseHandler;
import dmillerw.circuit.network.packet.core.BasePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/**
 * @author dmillerw
 */
public class S01ValueUpdate extends BasePacket {

    public static void sendInputUpdate(IConnectable connectable, int port) {
        final ChunkCoordinates coordinates = connectable.getCoordinates();
        final Chunk chunk = connectable.getWorld().getChunkFromBlockCoords(coordinates.posX, coordinates.posZ);

        S01ValueUpdate packet = new S01ValueUpdate();
        packet.updateType = 0;
        packet.target = coordinates;
        packet.targetPort = port;
        packet.value = connectable.getStateHandler().getInput(port);

        PacketHandler.sendToAllWatching(chunk, packet);
    }

    public static void sendOutputUpdate(IConnectable connectable, int port) {
        final ChunkCoordinates coordinates = connectable.getCoordinates();
        final Chunk chunk = connectable.getWorld().getChunkFromBlockCoords(coordinates.posX, coordinates.posZ);

        S01ValueUpdate packet = new S01ValueUpdate();
        packet.updateType = 1;
        packet.target = coordinates;
        packet.targetPort = port;
        packet.value = connectable.getStateHandler().getOutput(port);

        PacketHandler.sendToAllWatching(chunk, packet);
    }

    public byte updateType;

    public ChunkCoordinates target;
    public int targetPort;

    public WrappedValue value;

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(updateType);
        buf.writeInt(target.posX);
        buf.writeInt(target.posY);
        buf.writeInt(target.posZ);
        buf.writeInt(targetPort);
        buf.writeInt(value.getType().ordinal());
        value.writeToBuffer(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        updateType = buf.readByte();

        target = new ChunkCoordinates(buf.readInt(), buf.readInt(), buf.readInt());
        targetPort = buf.readInt();

        ValueType type = ValueType.values()[buf.readInt()];
        value = WrappedValue.valueOf(type, buf);
    }

    public static class Handler extends BaseHandler<S01ValueUpdate> {

        @Override
        public void handle(S01ValueUpdate message, MessageContext context) {
            World world = Minecraft.getMinecraft().theWorld;

            IConnectable connectable = (IConnectable) world.getTileEntity(
                    message.target.posX,
                    message.target.posY,
                    message.target.posZ);

            if (message.updateType == 0) {
                connectable.getStateHandler().setInput(message.targetPort, message.value);
//                connectable.getStateHandler().markDirty();
            } else {
                connectable.getStateHandler().setOutput(message.targetPort, message.value);
            }
        }
    }
}
