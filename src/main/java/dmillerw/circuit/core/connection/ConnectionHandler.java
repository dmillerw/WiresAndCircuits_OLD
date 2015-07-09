package dmillerw.circuit.core.connection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.network.packet.server.S01SetConnections;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author dmillerw
 */
public class ConnectionHandler {

    //TODO COMMENTS!

    public static final ConnectionHandler INSTANCE = new ConnectionHandler();

    private ConnectionHandler() {}

    /* CONNECTIONS */
    public Map<Integer, ArrayListMultimap<ChunkCoordinates, Connection>> connections = Maps.newHashMap();

    public ArrayListMultimap<ChunkCoordinates, Connection> get(World world) {
        return get(world.provider.dimensionId);
    }

    public ArrayListMultimap<ChunkCoordinates, Connection> get(int dimension) {
        ArrayListMultimap<ChunkCoordinates, Connection> map = connections.get(dimension);
        if (map == null) {
            map = ArrayListMultimap.create();
            connections.put(dimension, map);
        }
        return map;
    }

    private void print(ArrayListMultimap<ChunkCoordinates, Connection> map) {
        for (Map.Entry<ChunkCoordinates, Connection> entry : map.entries()) {
            System.out.println("ORIGIN: " + entry.getKey());
            System.out.println("TARGET: " + entry.getValue());
        }
    }

    public void clearConnections(World world) {
        get(world).clear();
    }

    private void removeExisting(World world, Connection check) {
        // First check to see if a connection to the target's input port already exists
        // If it does, we remove it

        for (Iterator<Map.Entry<ChunkCoordinates, Connection>> iterator = get(world).entries().iterator(); iterator.hasNext(); ) {
            Map.Entry<ChunkCoordinates, Connection> entry = iterator.next();
            Connection connection = entry.getValue();

            // If the connection leads to the same point...
            if (connection.target.equals(check.target)) {
                // ... and the same port ...
                if (connection.targetInputPort == check.targetInputPort) {
                    // ... we remove it
                    iterator.remove();
                }
            }
        }
    }

    public void addConnection(World world, ChunkCoordinates self, Connection connection, boolean update) {
        removeExisting(world, connection);
        get(world).get(self).add(connection);

        //TODO validate connections. see if blocks exist, etc

        if (update) {
            S01SetConnections.get(world.provider.dimensionId).sendToDimension(world.provider.dimensionId);

            //TODO This is clunky and bad :(
            IConnectable connectable = (IConnectable) world.getTileEntity(self.posX, self.posY, self.posZ);
            newQueue.add(new DelayedUpdate(world.provider.dimensionId, connection.target, connection.targetInputPort, connectable.getOutput(connection.selfOutputPort)));
        }
    }

    public void removeConnection(World world, ChunkCoordinates coordinates) {
        // First, remove any connections that originate from this point
        for (Connection connection : get(world).removeAll(coordinates)) {
            queueUpdate(new DelayedUpdate(world.provider.dimensionId, connection.target, connection.targetInputPort, WrappedValue.NULL));
        }

        // Then remove any connections that lead to this point
        for (Iterator<Map.Entry<ChunkCoordinates, Connection>> iterator = get(world).entries().iterator(); iterator.hasNext(); ) {
            Map.Entry<ChunkCoordinates, Connection> entry = iterator.next();
            Connection connection = entry.getValue();
            if (connection.target.equals(coordinates)) {
                iterator.remove();
            }
        }
    }

    /* UPDATES */
    private LinkedList<DelayedUpdate> newQueue = Lists.newLinkedList();
    private LinkedList<DelayedUpdate> queuedUpdates = Lists.newLinkedList();

    public void queueUpdate(World world, ChunkCoordinates self, int outputPort, WrappedValue value) {
        for (Connection connection : get(world).get(self)) {
            if (connection.selfOutputPort == outputPort) {
                queueUpdate(new DelayedUpdate(world.provider.dimensionId, connection.target, connection.targetInputPort, value));
            }
        }
    }

    public void queueUpdate(DelayedUpdate delayedUpdate) {
        newQueue.add(delayedUpdate);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null)
            return;

        // First, run through the current queue
        Iterator<DelayedUpdate> iterator = queuedUpdates.iterator();
        while (iterator.hasNext()) {
            DelayedUpdate update = iterator.next();
            World world = server.worldServerForDimension(update.dimension);
            TileEntity tileEntity = world.getTileEntity(update.targetPoint.posX, update.targetPoint.posY, update.targetPoint.posZ);
            if (tileEntity != null && tileEntity instanceof IConnectable) {
                ((IConnectable) tileEntity).onInputUpdate(update.targetPort, update.value);
            }
            iterator.remove();
        }

        // Just in case
        queuedUpdates.clear();

        // Then update the queue with any new updates
        queuedUpdates.addAll(newQueue);
        newQueue.clear();
    }
}
