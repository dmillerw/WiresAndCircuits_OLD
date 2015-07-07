package dmillerw.circuit.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.circuit.api.gate.GateRegistry;
import dmillerw.circuit.block.ModBlocks;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.core.handler.WorldEventHandler;
import dmillerw.circuit.gate.arithmatic.GateAdd;
import dmillerw.circuit.item.ModItems;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.initialize();
        ModItems.initialize();

        GateRegistry.registerGate("arithmatic:add", new GateAdd());

        FMLCommonHandler.instance().bus().register(ConnectionHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
