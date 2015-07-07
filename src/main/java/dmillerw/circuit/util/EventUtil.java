package dmillerw.circuit.util;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class EventUtil {

    public enum Type {
        FML,
        FORGE,
        BOTH
    }

    public static void register(Object object, Type type) {
        if (type == Type.FML || type == Type.BOTH)
            FMLCommonHandler.instance().bus().register(object);
        if (type == Type.FORGE || type == Type.BOTH)
            MinecraftForge.EVENT_BUS.register(object);
    }
}
