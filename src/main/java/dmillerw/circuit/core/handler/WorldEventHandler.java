package dmillerw.circuit.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.circuit.core.connection.Connection;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.util.NBTUtil;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author dmillerw
 */
public class WorldEventHandler {

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        NBTTagCompound tag = null;
        File file = getFile(event.world);

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                tag = CompressedStreamTools.readCompressed(fis);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (tag != null) {
            ConnectionHandler.INSTANCE.clearConnections(event.world);

            NBTTagList connections = tag.getTagList("connections", Constants.NBT.TAG_COMPOUND);
            for (int j=0; j<connections.tagCount(); j++) {
                NBTTagCompound fullTag = connections.getCompoundTagAt(j);

                ChunkCoordinates origin = NBTUtil.readChunkCoordinates(fullTag.getCompoundTag("origin"));
                Connection connection = Connection.readFromNBT(fullTag.getCompoundTag("connection"));

                ConnectionHandler.INSTANCE.addConnection(event.world, origin, connection, false);
            }
        }
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        NBTTagCompound tag = new NBTTagCompound();
        File file = getFile(event.world);

        NBTTagList list = new NBTTagList();

        for (Map.Entry<ChunkCoordinates, Connection> entry : ConnectionHandler.INSTANCE.get(event.world).entries()) {
            NBTTagCompound fullTag = new NBTTagCompound();

            NBTTagCompound origin = new NBTTagCompound();
            NBTUtil.writeChunkCoordinates(entry.getKey(), origin);
            fullTag.setTag("origin", origin);

            NBTTagCompound connection = new NBTTagCompound();
            entry.getValue().writeToNBT(connection);
            fullTag.setTag("connection", connection);

            list.appendTag(fullTag);
        }

        tag.setTag("connections", list);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            CompressedStreamTools.writeCompressed(tag, fos);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private File getFile(World world) {
        File root = DimensionManager.getCurrentSaveRootDirectory();
        File dir = new File(root, "WaCConnections");
        if (!dir.exists())
            dir.mkdir();

        return new File(dir, "DIM" + world.provider.dimensionId + ".conn");
    }
}
