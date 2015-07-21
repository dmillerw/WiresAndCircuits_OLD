package dmillerw.circuit.core.update;

import com.google.common.collect.Lists;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.util.Pair;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class BatchUpdate extends Update {

    public final Pair<Integer, WrappedValue>[] targets;

    public BatchUpdate(int dimension, ChunkCoordinates targetPoint, Pair<Integer, WrappedValue>[] targets) {
        super(dimension, targetPoint);

        this.targets = targets;
    }

    public BatchUpdate(int dimension, ChunkCoordinates targetPoint, Object ... args) {
        super(dimension, targetPoint);

        if (args.length % 2 != 0)
            throw new IllegalArgumentException("Bad BatchArgument construction!");

        List<Pair<Integer, WrappedValue>> list = Lists.newArrayList();
        for (int i=0; i<args.length + 1; i++) {
            list.add(Pair.of((Integer)args[i], (WrappedValue)args[i + 1]));
        }
        this.targets = list.toArray(new Pair[0]);
    }

    @Override
    public void fire() {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null)
            return;

        World world = server.worldServerForDimension(dimension);
        TileEntity tileEntity = world.getTileEntity(targetPoint.posX, targetPoint.posY, targetPoint.posZ);
        if (tileEntity != null && tileEntity instanceof IConnectable) {
            for (Pair<Integer, WrappedValue> pair : targets) {
                ((IConnectable) tileEntity).getStateHandler().setInput(pair.l, pair.r);
            }
            ((IConnectable) tileEntity).getStateHandler().markDirty();
        }
    }
}
