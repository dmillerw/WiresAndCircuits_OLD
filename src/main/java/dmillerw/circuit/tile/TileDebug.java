package dmillerw.circuit.tile;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.value.ValueType;
import dmillerw.circuit.api.value.WrappedValue;
import dmillerw.circuit.tile.core.TileCoreConnectable;

/**
 * @author dmillerw
 */
public class TileDebug extends TileCoreConnectable implements IConnectable {

    @Override
    public ValueType[] getInputTypes() {
        return new ValueType[] {ValueType.ANY};
    }

    @Override
    public ValueType[] getOutputTypes() {
        return new ValueType[0];
    }

    @Override
    public void onInputUpdate(int index, WrappedValue value) {
        super.onInputUpdate(index, value);
        System.out.println("DEBUG: Port " + index + " was set to " + value.toJString());
    }
}
