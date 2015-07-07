package dmillerw.circuit;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.circuit.proxy.CommonProxy;

/**
 * @author dmillerw
 */
@Mod(modid = WiresAndCircuits.ID, name = WiresAndCircuits.NAME, version = WiresAndCircuits.VERSION)
public class WiresAndCircuits {

    public static final String ID = "WaC";
    public static final String NAME = "Wires and Circuits";
    public static final String VERSION = "%MOD_VERSION%";

    public static String prefix(String str) {
        return ID + ":" + str;
    }

    @Mod.Instance(ID)
    public static WiresAndCircuits instance;

    @SidedProxy(serverSide = "dmillerw.circuit.proxy.CommonProxy", clientSide = "dmillerw.circuit.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
