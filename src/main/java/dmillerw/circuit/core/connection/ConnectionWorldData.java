package dmillerw.circuit.core.connection;

import com.google.common.collect.Maps;
import dmillerw.circuit.util.NBTUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.Map;

/**
 * @author dmillerw
 */
public class ConnectionWorldData extends WorldSavedData {

    private static final String TAG = "WAC-Connection_data";
    private static final Map<Integer, ConnectionWorldData> INSTANCES = Maps.newHashMap();

    public static void markDirty(int dimension) {
        INSTANCES.get(dimension).markDirty();
    }

    //TODO Connections are still lost on a complete client restart, but not on a simple server shutdown
    public static ConnectionWorldData load(World world) {
        ConnectionWorldData data = (ConnectionWorldData) world.loadItemData(ConnectionWorldData.class, TAG);
        if (data == null) {
            data = new ConnectionWorldData(TAG);
            world.setItemData(TAG, data);
        }
        INSTANCES.put(world.provider.dimensionId, data);
        return data;
    }

    public static void unload(World world) {
        INSTANCES.remove(world.provider.dimensionId);
    }

    public ConnectionWorldData(String s) {
        super(s);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        NBTTagList dimensions = new NBTTagList();
        for (int dimension : ConnectionHandler.INSTANCE.connections.keySet()) {
            dimensions.appendTag(new NBTTagDouble(dimension)); // NBTTagList doesn't have a method for getting ints...

            NBTTagList list = new NBTTagList();

            for (Map.Entry<ChunkCoordinates, Connection> entry : ConnectionHandler.INSTANCE.connections.get(dimension).entries()) {
                NBTTagCompound fullTag = new NBTTagCompound();

                NBTTagCompound origin = new NBTTagCompound();
                NBTUtil.writeChunkCoordinates(entry.getKey(), origin);
                fullTag.setTag("origin", origin);

                NBTTagCompound connection = new NBTTagCompound();
                entry.getValue().writeToNBT(connection);
                fullTag.setTag("connection", connection);

                list.appendTag(fullTag);
            }

            tag.setTag("connections_" + dimension, list);
        }
        tag.setTag("dimensions", dimensions);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        NBTTagList dimensions = tag.getTagList("dimensions", Constants.NBT.TAG_DOUBLE);
        for (int i=0; i<dimensions.tagCount(); i++) {
            int dimension = (int) dimensions.func_150309_d(i);

            NBTTagList connections = tag.getTagList("connections_" + dimension, Constants.NBT.TAG_COMPOUND);
            for (int j=0; j<connections.tagCount(); j++) {
                NBTTagCompound fullTag = connections.getCompoundTagAt(j);

                ChunkCoordinates origin = NBTUtil.readChunkCoordinates(fullTag.getCompoundTag("origin"));
                Connection connection = Connection.readFromNBT(fullTag.getCompoundTag("connection"));

                ConnectionHandler.INSTANCE.addConnection(dimension, origin, connection);
            }
        }
    }

    // This is kind of bad, I know, but it won't FRIGGIN SAVE OTHERWISE! :(
    // Too tired to debug
    @Override
    public boolean isDirty() {
        return true;
    }
}
