package dmillerw.circuit.api.value;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagString;

/**
 * @author dmillerw
 */
public abstract class WrappedValue {

    public static final WrappedNull NULL = new WrappedNull();

    public static WrappedValue valueOf(NBTBase value) {
        if (value == null)
            return NULL;

        if (value instanceof NBTTagByte)
            return valueOf(((NBTTagByte) value).func_150290_f() == 1);
        else if (value instanceof NBTTagDouble)
            return valueOf(((NBTTagDouble) value).func_150286_g());
        else if (value instanceof NBTTagString)
            return valueOf(((NBTTagString) value).func_150285_a_());
        else
            return NULL;
    }

    public static WrappedValue valueOf(ValueType type, ByteBuf buffer) {
        switch (type) {
            case BOOLEAN:
                return new WrappedBoolean(buffer.readBoolean());
            case NUMBER:
                return new WrappedNumber(buffer.readDouble());
            case STRING:
                return new WrappedString(ByteBufUtils.readUTF8String(buffer));
            default:
                return NULL;
        }
    }

    public static WrappedValue cast(ValueType type, WrappedValue value) {
        if (value.getType() == type)
            return value;

        switch (type) {
            case BOOLEAN: {
                switch (value.getType()) {
                    case BOOLEAN: return value;
                    case NUMBER: return WrappedValue.valueOf(((WrappedNumber)value).value != 0);
                    case STRING: return WrappedValue.valueOf(Boolean.parseBoolean(((WrappedString)value).value));
                    default: return WrappedValue.valueOf(false);
                }
            }

            case NUMBER: {
                switch (value.getType()) {
                    case BOOLEAN: return WrappedValue.valueOf(((WrappedBoolean)value).value ? 1 : 0);
                    case NUMBER: return value;
                    case STRING: return WrappedValue.valueOf(Double.parseDouble(((WrappedString)value).value));
                    default: return WrappedValue.valueOf(0);
                }
            }

            case STRING: {
                switch (value.getType()) {
                    case BOOLEAN: return WrappedValue.valueOf(Boolean.toString(((WrappedBoolean)value).value));
                    case NUMBER: return WrappedValue.valueOf(Double.toString(((WrappedNumber)value).value));
                    case STRING: return value;
                    default: return WrappedValue.valueOf("");
                }
            }

            default: return NULL;
        }
    }

    public static WrappedBoolean valueOf(boolean value) {
        return new WrappedBoolean(value);
    }
    public static WrappedNumber valueOf(int value) {
        return new WrappedNumber(value);
    }
    public static WrappedNumber valueOf(double value) {
        return new WrappedNumber(value);
    }
    public static WrappedString valueOf(String value) {
        return new WrappedString(value);
    }

    public abstract ValueType getType();
    public abstract NBTBase getNBTTag();
    public abstract void writeToBuffer(ByteBuf buf);

    public final boolean toBoolean() {
        return ((WrappedBoolean)cast(ValueType.BOOLEAN)).value;
    }

    public final double toNumber() {
        return ((WrappedNumber)cast(ValueType.NUMBER)).value;
    }

    public final String toJString() {
        return ((WrappedString)cast(ValueType.STRING)).value;
    }

    public final WrappedValue cast(ValueType type) {
        return cast(type, this);
    }

    public boolean equals(WrappedValue value) {
        return false;
    }
}
