package dmillerw.circuit.api.tile;

import dmillerw.circuit.api.value.ValueType;
import dmillerw.circuit.api.value.WrappedValue;

/**
 * @author dmillerw
 */
public interface IConnectable {

    ValueType[] getInputTypes();
    ValueType[] getOutputTypes();

    /**
     * Fires whenever another connectable object dispatches an update to this connectable
     */
    void onInputUpdate(int index, WrappedValue value);

    /**
     * Retrieves the cached input value. Mainly used for gate computation
     */
    WrappedValue getInput(int index);

    WrappedValue getOutput(int index);

    /**
     * Updates the cached output value. Value updates aren't actually sent until {@link IConnectable#sendPortUpdate(int)} is called
     */
    void setOutput(int index, WrappedValue value);

    /**
     * Actually sends the output value to all connected blocks. Passing '-1' as the index will update all connected
     * blocsk on all ports. A complete update
     */
    void sendPortUpdate(int index);
}

