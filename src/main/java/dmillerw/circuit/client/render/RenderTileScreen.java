package dmillerw.circuit.client.render;

import dmillerw.circuit.tile.TileScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderTileScreen extends TileRenderer<TileScreen> {

    private static IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("wac:models/screen.tcn"));
    private static ResourceLocation texture = new ResourceLocation("wac:textures/model/screen.png");

    @Override
    public void renderTileAt(TileScreen tile, double x, double y, double z, float partial) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y - 0.5, z + 0.5);

        //TODO Less hard-coding :P

        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glScaled(0.0625F / 2, 0.0625F / 2, 0.0625F / 2);
        GL11.glTranslated(-2, 36, -13.75);
        GL11.glRotated(180, 1, 0, 0);
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String s = tile.getInput(0).toJString();
        fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2 + 2, 0, 0xFFFFFF);
        GL11.glPopMatrix();

        GL11.glTranslated(0, 1, 0);
        switch (tile.side) {
            case DOWN:
                GL11.glRotated(0, 0, 0, 0);
                break;
            case UP:
                GL11.glRotated(180, 1, 0, 0);
                break;
            case NORTH:
                GL11.glRotated(90, 1, 0, 0);
                break;
            case SOUTH:
                GL11.glRotated(-90, 1, 0, 0);
                break;
            case EAST:
                GL11.glRotated(90, 0, 0, 1);
                break;
            case WEST:
                GL11.glRotated(-90, 0, 0, 1);
                break;
            default:
                break;
        }
        GL11.glTranslated(0, -0.5, 0);

        bindTexture(texture);
        model.renderAll();

        GL11.glPopMatrix();
    }
}
