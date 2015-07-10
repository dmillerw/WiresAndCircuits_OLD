package dmillerw.circuit.api.value;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

/**
 * @author dmillerw
 */
public class WrappedString extends WrappedValue {

    protected final String value;

    protected WrappedString(String value) {
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return ValueType.STRING;
    }

    @Override
    public NBTBase getNBTTag() {
        return new NBTTagString(value);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, value);
    }

    @Override
    public boolean equals(WrappedValue value) {
        return value instanceof WrappedString && this.value.equals(((WrappedString) value).value);
    }
}
