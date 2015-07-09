package dmillerw.circuit.api.value;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;

/**
 * @author dmillerw
 */
public class WrappedBoolean extends WrappedValue {

    private final boolean value;

    protected WrappedBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return ValueType.BOOLEAN;
    }

    @Override
    public boolean toBoolean() {
        return value;
    }

    @Override
    public double toNumber() {
        return value ? 1 : 0;
    }

    @Override
    public String toJString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean isBoolean() {
        return true;
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
        return new NBTTagByte((byte) (value ? 1 : 0));
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        buf.writeBoolean(value);
    }
}
