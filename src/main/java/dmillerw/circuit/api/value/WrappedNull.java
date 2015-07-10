package dmillerw.circuit.api.value;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;

/**
 * @author dmillerw
 */
public class WrappedNull extends WrappedValue {

    @Override
    public ValueType getType() {
        return ValueType.NULL;
    }

    @Override
    public NBTBase getNBTTag() {
        return null;
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {

    }
}
