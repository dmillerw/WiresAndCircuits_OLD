package dmillerw.circuit.core.update;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.server.MinecraftServer;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author dmillerw
 */
public class UpdateHandler {

    public static final UpdateHandler INSTANCE = new UpdateHandler();

    private LinkedList<Update> newQueue = Lists.newLinkedList();
    private LinkedList<Update> queuedUpdates = Lists.newLinkedList();

    public void queueUpdate(Update update) {
        newQueue.add(update);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null)
            return;

//        if (!newQueue.isEmpty()) {
//            System.out.println("NEW: " + newQueue);
//        }
//        if (!queuedUpdates.isEmpty()) {
//            System.out.println("QUEUED: " + queuedUpdates);
//        }

        // First, run through the current queue
        Iterator<Update> iterator = queuedUpdates.iterator();
        while (iterator.hasNext()) {
            Update update = iterator.next();
            update.fire();
            iterator.remove();
        }

        // Just in case
        queuedUpdates.clear();

        // Then update the queue with any new updates
        queuedUpdates.addAll(newQueue);
        newQueue.clear();
    }
}
