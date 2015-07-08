package dmillerw.circuit.core.connection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
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

    public static final ConnectionHandler INSTANCE = new ConnectionHandler();

    private ConnectionHandler() {}

    /* CONNECTIONS */
    public Map<Integer, ArrayListMultimap<ChunkCoordinates, Connection>> connections = Maps.newHashMap();

    private ArrayListMultimap<ChunkCoordinates, Connection> get(World world) {
        return get(world.provider.dimensionId);
    }

    private ArrayListMultimap<ChunkCoordinates, Connection> get(int dimension) {
        if (connections.get(dimension) == null) {
            ArrayListMultimap<ChunkCoordinates, Connection> mm = ArrayListMultimap.create();
            connections.put(dimension, mm);
        }
        return connections.get(dimension);
    }

    public void addConnection(int dimension, ChunkCoordinates self, Connection connection) {
        get(dimension).get(self).add(connection);
    }

    public void addConnection(World world, ChunkCoordinates self, Connection connection) {
        get(world).get(self).add(connection);
    }

    /* UPDATES */
    private LinkedList<DelayedUpdate> newQueue = Lists.newLinkedList();
    private LinkedList<DelayedUpdate> queuedUpdates = Lists.newLinkedList();

    public void queueUpdate(World world, ChunkCoordinates self, int outputPort, WrappedValue value) {
        for (Connection connection : get(world).get(self)) {
            if (connection.selfOutputPort == outputPort) {
                queueUpdate(new DelayedUpdate(world.provider.dimensionId, connection.target, outputPort, value));
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
    }
}
