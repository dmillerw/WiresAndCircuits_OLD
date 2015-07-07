package dmillerw.circuit.client.render;

import dmillerw.circuit.tile.core.TileCore;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author dmillerw
 */
public abstract class TileRenderer<T extends TileCore> extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partial) {

    }

    public abstract void renderTileAt(T tile, double x, double y, double z, float partial);
}
