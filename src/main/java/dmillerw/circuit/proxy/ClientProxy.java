package dmillerw.circuit.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.circuit.client.handler.ClientConnectionHandler;
import dmillerw.circuit.client.handler.ClientEventHandler;
import dmillerw.circuit.client.model.loader.FixedTechneModelLoader;
import dmillerw.circuit.client.render.RenderTileScreen;
import dmillerw.circuit.tile.TileScreen;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        AdvancedModelLoader.registerModelHandler(new FixedTechneModelLoader());

        ClientRegistry.bindTileEntitySpecialRenderer(TileScreen.class, new RenderTileScreen());

        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(ClientConnectionHandler.INSTANCE);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
