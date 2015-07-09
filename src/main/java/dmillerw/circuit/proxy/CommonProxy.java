package dmillerw.circuit.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.circuit.api.gate.GateRegistry;
import dmillerw.circuit.block.ModBlocks;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.core.connection.UpdateHandler;
import dmillerw.circuit.core.handler.WorldEventHandler;
import dmillerw.circuit.gate.arithmatic.GateAdd;
import dmillerw.circuit.item.ModItems;
import dmillerw.circuit.network.PacketHandler;
import dmillerw.circuit.util.EventUtil;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.initialize();
        ModItems.initialize();

        PacketHandler.initialize();

        GateRegistry.registerGate("arithmatic:add", new GateAdd());

        EventUtil.register(ConnectionHandler.INSTANCE, EventUtil.Type.FML);
        EventUtil.register(UpdateHandler.INSTANCE, EventUtil.Type.FML);
        EventUtil.register(new WorldEventHandler(), EventUtil.Type.FORGE);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
