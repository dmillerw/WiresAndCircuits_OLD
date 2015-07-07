package dmillerw.circuit.tile;

import dmillerw.circuit.api.value.ValueType;
import dmillerw.circuit.tile.core.TileCoreConnectable;

/**
 * @author dmillerw
 */
public class TileScreen extends TileCoreConnectable {

    @Override
    public ValueType[] getInputTypes() {
        return new ValueType[] {ValueType.ANY};
    }

    @Override
    public ValueType[] getOutputTypes() {
        return new ValueType[0];
    }
}
