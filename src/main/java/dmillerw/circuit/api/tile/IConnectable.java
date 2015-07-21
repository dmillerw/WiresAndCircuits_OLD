package dmillerw.circuit.api.tile;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public interface IConnectable {

    World getWorld();
    ChunkCoordinates getCoordinates();

    int getInputCount();
    int getOutputCount();

    IStateHandler getStateHandler();

    void onStateUpdate(int[] dirtyPorts);
}

