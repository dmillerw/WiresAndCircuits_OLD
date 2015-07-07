package dmillerw.circuit.block;

import dmillerw.circuit.tile.core.TileCore;
import dmillerw.circuit.tile.TileRedstoneAdapter;
import net.minecraft.block.material.Material;

/**
 * @author dmillerw
 */
public class BlockRedstoneAdapter extends BlockTileCore {

    public BlockRedstoneAdapter() {
        super(Material.iron);
    }

    @Override
    public TileCore getTileEntity(int metadata) {
        return new TileRedstoneAdapter();
    }
}
