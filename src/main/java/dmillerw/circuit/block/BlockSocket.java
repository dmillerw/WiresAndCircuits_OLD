package dmillerw.circuit.block;

import dmillerw.circuit.tile.core.TileCore;
import dmillerw.circuit.tile.TileSocket;
import net.minecraft.block.material.Material;

/**
 * @author dmillerw
 */
public class BlockSocket extends BlockTileCore {

    public BlockSocket() {
        super(Material.iron);
    }

    @Override
    public TileCore getTileEntity(int metadata) {
        return new TileSocket();
    }
}
