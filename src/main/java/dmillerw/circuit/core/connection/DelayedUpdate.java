package dmillerw.circuit.core.connection;

import dmillerw.circuit.api.value.WrappedValue;
import net.minecraft.util.ChunkCoordinates;

/**
 * @author dmillerw
 */
public class DelayedUpdate {

    public final int dimension;
    public final ChunkCoordinates targetPoint;
    public final int targetPort;
    public final WrappedValue value;

    public DelayedUpdate(int dimension, ChunkCoordinates targetPoint, int targetPort, WrappedValue value) {
        this.dimension = dimension;
        this.targetPoint = targetPoint;
        this.targetPort = targetPort;
        this.value = value;
    }
}
