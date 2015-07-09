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
    public boolean toBoolean() {
        return false;
    }

    @Override
    public double toNumber() {
        return 0;
    }

    @Override
    public String toJString() {
        return "";
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isJString() {
        return false;
    }

    @Override
    public NBTBase getNBTTag() {
        return null;
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {

    }
}
