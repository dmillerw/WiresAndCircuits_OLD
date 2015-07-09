package dmillerw.circuit.core.connection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import dmillerw.circuit.api.tile.IConnectable;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
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

    public List<Connection> get(IConnectable connectable) {
        return get(connectable.getWorld()).get(connectable.getCoordinates());
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
                    //TODO Do we reset the values when the block is removed? or just allow the target to keep the last known value
//                    IConnectable connectable = (IConnectable) world.getTileEntity(connection.target.posX, connection.target.posY, connection.target.posZ);
//                    connectable.onConnectionRemoved(connection.targetInputPort);

                    // ... we remove it
                    iterator.remove();
                }
            }
        }
    }

    public void addConnection(World world, ChunkCoordinates self, Connection connection, boolean update) {
        if (update)
            removeExisting(world, connection);

        get(world).get(self).add(connection);

        //TODO validate connections. see if blocks exist, etc

        if (update) {
            IConnectable connectable = (IConnectable) world.getTileEntity(self.posX, self.posY, self.posZ);
            connectable.onConnectionEstablished(connection.target, connection.selfOutputPort);
        }
    }

    public void removeConnection(World world, ChunkCoordinates coordinates) {
        // First, remove any connections that originate from this point
        for (Connection connection : get(world).removeAll(coordinates)) {
            //TODO Do we reset the values when the block is removed? or just allow the target to keep the last known value
//            IConnectable connectable = (IConnectable) world.getTileEntity(connection.target.posX, connection.target.posY, connection.target.posZ);
//            connectable.onConnectionRemoved(connection.targetInputPort);
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
}
