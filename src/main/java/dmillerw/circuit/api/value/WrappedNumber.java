package dmillerw.circuit.api.value;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;

/**
 * @author dmillerw
 */
public class WrappedNumber extends WrappedValue {

    private final double value;

    protected WrappedNumber(double value) {
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return ValueType.NUMBER;
    }

    @Override
    public boolean toBoolean() {
        return false;
    }

    @Override
    public double toNumber() {
        return value;
    }

    @Override
    public String toJString() {
        return Double.toString(value);
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isJString() {
        return false;
    }

    @Override
    public NBTBase getNBTTag() {
        return new NBTTagDouble(value);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        buf.writeDouble(value);
    }
}
