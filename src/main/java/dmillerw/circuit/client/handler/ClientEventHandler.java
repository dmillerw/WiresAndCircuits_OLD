package dmillerw.circuit.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.item.ItemWireTool;
import dmillerw.circuit.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class ClientEventHandler {

    private static MovingObjectPosition mousedOverGate = null;
    public static boolean initial = true;
    private static int portCount = 0;
    public static int index = 0;

    public static int getEventDWheel() {
        if (GuiScreen.isCtrlKeyDown() && mousedOverGate != null && portCount > 0) {
            int dwheel = Mouse.getDWheel();
            if (dwheel < 0) {
                index--;
                if (index < 0)
                    index = portCount - 1;
            } else if (dwheel > 0) {
                index++;
                if (index >= portCount)
                    index = 0;
            }
            return 0;
        } else {
            return Mouse.getEventDWheel();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        final Minecraft mc = Minecraft.getMinecraft();
        final EntityPlayer player = mc.thePlayer;

        mousedOverGate = null;

        if (mc.isGamePaused())
            return;

        if (mc.currentScreen != null)
            return;

        if (mc.theWorld == null)
            return;

        if (player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.wireTool)
            return;

        MovingObjectPosition block = mc.objectMouseOver;

        if (block != null && block.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            TileEntity tile = mc.theWorld.getTileEntity(block.blockX, block.blockY, block.blockZ);
            if (tile != null && tile instanceof IConnectable) {
                if (mousedOverGate != null) {
                    if (block.blockX != mousedOverGate.blockX || block.blockY != mousedOverGate.blockY || block.blockZ != mousedOverGate.blockZ)
                        index = 0;
                } else {
                    index = 0;
                }

                mousedOverGate = block;
                initial = ItemWireTool.getTarget(player.getHeldItem()) == null;
                portCount = initial ? ((IConnectable) tile).getInputTypes().length : ((IConnectable) tile).getOutputTypes().length;

                if (portCount == 0)
                    mousedOverGate = null;
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        final Minecraft mc = Minecraft.getMinecraft();
        final EntityPlayer player = mc.thePlayer;

        if (mc.theWorld == null)
            return;

        if (mousedOverGate != null) {
            final ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

            final int minY = res.getScaledHeight() / 2 + 20;
            final int maxY = minY + 10;
            final int minX = res.getScaledWidth() / 2 - 50;
            final int maxX = minX + 100;

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glDisable(GL11.GL_TEXTURE_2D);

            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(0F, 0F, 0F, 0.50F);
            tessellator.addVertex(minX, maxY, 0);
            tessellator.addVertex(maxX, maxY, 0);
            tessellator.addVertex(maxX, minY, 0);
            tessellator.addVertex(minX, minY, 0);
            tessellator.draw();

            GL11.glEnable(GL11.GL_TEXTURE_2D);

            final FontRenderer fontRenderer = mc.fontRenderer;
            final String str = initial ? "OUTPUT" : "INPUT";
            fontRenderer.drawString(str, (res.getScaledWidth() / 2) - (fontRenderer.getStringWidth(str) / 2), maxY + 10, 0x00FF00);

            GL11.glPushMatrix();
            GL11.glTranslated(-1, 0.25, 0);

            fontRenderer.drawString(Integer.toString(index), res.getScaledWidth() / 2, minY + 1, 0x00FF00);

            // Mm, hard-coding
            // Draw the previous two numbers
            int prev = index - 1;
            if (prev < 0)
                prev = portCount - 1;
            fontRenderer.drawString(Integer.toString(prev), res.getScaledWidth() / 2 - 20, minY + 1, 0xFFFFFF);

            prev--;
            if (prev < 0)
                prev = portCount - 1;
            fontRenderer.drawString(Integer.toString(prev), res.getScaledWidth() / 2 - 40, minY + 1, 0xFFFFFF);

            // Then the next two
            int next = index + 1;
            if (next >= portCount)
                next = 0;
            fontRenderer.drawString(Integer.toString(next), res.getScaledWidth() / 2 + 20, minY + 1, 0xFFFFFF);

            next++;
            if (next >= portCount)
                next = 0;
            fontRenderer.drawString(Integer.toString(next), res.getScaledWidth() / 2 + 40, minY + 1, 0xFFFFFF);

            GL11.glPopMatrix();

            GL11.glPopMatrix();
        }
    }
}
