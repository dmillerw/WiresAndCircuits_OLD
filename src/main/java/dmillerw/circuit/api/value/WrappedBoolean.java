package dmillerw.circuit.api.value;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;

/**
 * @author dmillerw
 */
public class WrappedBoolean extends WrappedValue {

    protected final boolean value;

    protected WrappedBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return ValueType.BOOLEAN;
    }

    @Override
    public NBTBase getNBTTag() {
        return new NBTTagByte((byte) (value ? 1 : 0));
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        buf.writeBoolean(value);
    }

    @Override
    public boolean equals(WrappedValue value) {
        return value instanceof WrappedBoolean && this.value == ((WrappedBoolean) value).value;
    }
}
