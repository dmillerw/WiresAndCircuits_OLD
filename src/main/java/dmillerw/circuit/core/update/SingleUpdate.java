package dmillerw.circuit.core.update;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class SingleUpdate extends Update {

    private final int targetPort;
    private final WrappedValue value;

    public SingleUpdate(int dimension, ChunkCoordinates targetPoint, int targetPort, WrappedValue value) {
        super(dimension, targetPoint);
        this.targetPort = targetPort;
        this.value = value;
    }

    public void fire() {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null)
            return;

        World world = server.worldServerForDimension(dimension);
        TileEntity tileEntity = world.getTileEntity(targetPoint.posX, targetPoint.posY, targetPoint.posZ);
        if (tileEntity != null && tileEntity instanceof IConnectable) {
            ((IConnectable) tileEntity).getStateHandler().setInput(targetPort, value);
            ((IConnectable) tileEntity).getStateHandler().markDirty();
        }
    }
}
