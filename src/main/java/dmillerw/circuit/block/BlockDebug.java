package dmillerw.circuit.block;

import dmillerw.circuit.tile.core.TileCore;
import dmillerw.circuit.tile.tool.TileDebug;
import net.minecraft.block.material.Material;

/**
 * @author dmillerw
 */
public class BlockDebug extends BlockTileCore {

    public BlockDebug() {
        super(Material.iron);
    }

    @Override
    public TileCore getTileEntity(int metadata) {
        return new TileDebug();
    }
}
