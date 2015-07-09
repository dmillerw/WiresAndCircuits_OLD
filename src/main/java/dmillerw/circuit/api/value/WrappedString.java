package dmillerw.circuit.api.value;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

/**
 * @author dmillerw
 */
public class WrappedString extends WrappedValue {

    private final String value;

    protected WrappedString(String value) {
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return ValueType.STRING;
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
        return value;
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
        return true;
    }

    @Override
    public NBTBase getNBTTag() {
        return new NBTTagString(value);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, value);
    }
}
