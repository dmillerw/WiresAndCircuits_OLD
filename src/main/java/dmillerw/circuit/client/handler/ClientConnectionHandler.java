package dmillerw.circuit.client.handler;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.circuit.network.packet.server.S01SetConnections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.Map;
import java.util.Set;

/**
 * @author dmillerw
 */
public class ClientConnectionHandler {

    public static final ClientConnectionHandler INSTANCE = new ClientConnectionHandler();

    private Map<ChunkCoordinates, Set<ChunkCoordinates>> connections = Maps.newHashMap();

    public void handleReset(S01SetConnections packet) {
        connections = packet.connections;
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        RenderHelper.enableStandardItemLighting();

        EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
        double dx = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * event.partialTicks;
        double dy = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * event.partialTicks;
        double dz = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * event.partialTicks;

        for (Map.Entry<ChunkCoordinates, Set<ChunkCoordinates>> entry : connections.entrySet()) {
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(GL11.GL_LINES);

            tessellator.setColorOpaque_I(0x00FF00);

            ChunkCoordinates origin = entry.getKey();
            for (ChunkCoordinates target : entry.getValue()) {
                tessellator.addVertex(origin.posX + 0.5 - dx, origin.posY + 0.5 - dy, origin.posZ + 0.5 - dz);
                tessellator.addVertex(target.posX + 0.5 - dx, target.posY + 0.5 - dy, target.posZ + 0.5 - dz);
                tessellator.draw();
            }

        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }
}
