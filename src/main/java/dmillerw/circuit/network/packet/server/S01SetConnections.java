package dmillerw.circuit.network.packet.server;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.circuit.client.handler.ClientConnectionHandler;
import dmillerw.circuit.core.connection.Connection;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.network.packet.core.BaseHandler;
import dmillerw.circuit.network.packet.core.BasePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChunkCoordinates;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author dmillerw
 */
public class S01SetConnections extends BasePacket {

    public static S01SetConnections get(int dimension) {
        ImmutableMap.Builder<ChunkCoordinates, Set<ChunkCoordinates>> builder = ImmutableMap.builder();
        ArrayListMultimap<ChunkCoordinates, Connection> map = ConnectionHandler.INSTANCE.get(dimension);

        Collection<ChunkCoordinates> origins = map.keys();

        for (ChunkCoordinates origin : origins) {
            ImmutableSet.Builder<ChunkCoordinates> targets = ImmutableSet.builder();
            for (Connection connection : map.values()) {
                targets.add(connection.target);
            }
            builder.put(origin, targets.build());
        }

        S01SetConnections packet = new S01SetConnections();
        packet.connections = builder.build();
        System.out.println(packet.connections);
        return packet;
    }

    public Map<ChunkCoordinates, Set<ChunkCoordinates>> connections;

    @Override
    public void toBytes(ByteBuf buf) {
        // Total size
        buf.writeInt(connections.size());

        for (Map.Entry<ChunkCoordinates, Set<ChunkCoordinates>> entry : connections.entrySet()) {
            final ChunkCoordinates key = entry.getKey();
            final Set<ChunkCoordinates> val = entry.getValue();

            buf.writeInt(key.posX);
            buf.writeInt(key.posY);
            buf.writeInt(key.posZ);

            // Sub size
            buf.writeInt(val.size());

            for (ChunkCoordinates coordinates : val) {
                buf.writeInt(coordinates.posX);
                buf.writeInt(coordinates.posY);
                buf.writeInt(coordinates.posZ);
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ImmutableMap.Builder<ChunkCoordinates, Set<ChunkCoordinates>> connections = ImmutableMap.builder();

        int size = buf.readInt();

        for (int i=0; i<size; i++) {
            ChunkCoordinates origin = new ChunkCoordinates(buf.readInt(), buf.readInt(), buf.readInt());
            ImmutableSet.Builder<ChunkCoordinates> targets = ImmutableSet.builder();

            int subSize = buf.readInt();
            for (int j=0; j<subSize; j++) {
                targets.add(new ChunkCoordinates(buf.readInt(), buf.readInt(), buf.readInt()));
            }

            connections.put(origin, targets.build());
        }

        this.connections = connections.build();
    }

    public static class Handler extends BaseHandler<S01SetConnections> {

        @Override
        public void handle(S01SetConnections message, MessageContext context) {
            ClientConnectionHandler.INSTANCE.handleReset(message);
        }
    }
}
