package dmillerw.circuit.core.update;

import net.minecraft.util.ChunkCoordinates;

/**
 * @author dmillerw
 */
public abstract class Update {

    public final int dimension;
    public final ChunkCoordinates targetPoint;

    public Update(int dimension, ChunkCoordinates targetPoint) {
        this.dimension = dimension;
        this.targetPoint = targetPoint;
    }

    public abstract void fire();
}
