package dmillerw.circuit.client.render;

import dmillerw.circuit.tile.TileScreen;
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

        //TODO Fix screen model. It's all upside-down n' shit

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
