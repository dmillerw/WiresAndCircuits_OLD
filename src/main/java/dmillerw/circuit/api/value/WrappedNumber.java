package dmillerw.circuit.api.value;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;

/**
 * @author dmillerw
 */
public class WrappedNumber extends WrappedValue {

    protected final double value;

    protected WrappedNumber(double value) {
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return ValueType.NUMBER;
    }

    @Override
    public NBTBase getNBTTag() {
        return new NBTTagDouble(value);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        buf.writeDouble(value);
    }

    @Override
    public boolean equals(WrappedValue value) {
        return value instanceof WrappedNumber && this.value == ((WrappedNumber) value).value;
    }
}
