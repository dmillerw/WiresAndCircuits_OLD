package dmillerw.circuit.tile.tool;

import dmillerw.circuit.tile.core.TileConnectable;

/**
 * @author dmillerw
 */
public class TileDebug extends TileConnectable {

    /* ICONNECTABLE */
    @Override
    public int getInputCount() {
        return 1;
    }

    @Override
    public int getOutputCount() {
        return 0;
    }

    @Override
    public void onStateUpdate(int[] dirtyPorts) {
        for (int i : dirtyPorts) {
            System.out.println("DEBUG: Port " + i + " changed to " + getStateHandler().getInput(i).toJString());
        }
    }
}
