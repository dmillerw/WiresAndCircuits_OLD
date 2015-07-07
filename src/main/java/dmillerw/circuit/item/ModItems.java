package dmillerw.circuit.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class ModItems {

    public static Item wireTool;

    public static void initialize() {
        wireTool = new ItemWireTool().setUnlocalizedName("wireTool");
        GameRegistry.registerItem(wireTool, "wireTool");
    }
}
