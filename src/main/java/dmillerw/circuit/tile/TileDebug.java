package dmillerw.circuit.tile;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.tile.core.TileCoreConnectable;

/**
 * @author dmillerw
 */
public class TileDebug extends TileCoreConnectable implements IConnectable {

    @Override
    public int getInputCount() {
        return 1;
    }

    @Override
    public int getOutputCount() {
        return 0;
    }

    @Override
    public void setInput(int index, WrappedValue value) {
        super.setInput(index, value);
        System.out.println("DEBUG: Port " + index + " was set to " + value.toJString());
    }
}
