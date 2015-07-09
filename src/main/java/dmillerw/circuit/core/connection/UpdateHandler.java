package dmillerw.circuit.core.connection;

import com.google.common.collect.Lists;
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

/**
 * @author dmillerw
 */
public class UpdateHandler {

    public static final UpdateHandler INSTANCE = new UpdateHandler();

    private LinkedList<DelayedUpdate> newQueue = Lists.newLinkedList();
    private LinkedList<DelayedUpdate> queuedUpdates = Lists.newLinkedList();

    /**
     * Queue an update to the target point and port, with the specified data
     */
    public void queueUpdate(World world, ChunkCoordinates target, int port, WrappedValue value) {
        queueUpdate(new DelayedUpdate(world.provider.dimensionId, target, port, value));
    }

    private void queueUpdate(DelayedUpdate delayedUpdate) {
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
                ((IConnectable) tileEntity).setInput(update.targetPort, update.value);
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
