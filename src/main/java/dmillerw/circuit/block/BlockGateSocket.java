package dmillerw.circuit.block;

import dmillerw.circuit.tile.core.TileCore;
import dmillerw.circuit.tile.tool.TileGateSocket;
import net.minecraft.block.material.Material;

/**
 * @author dmillerw
 */
public class BlockGateSocket extends BlockTileCore {

    public BlockGateSocket() {
        super(Material.iron);
    }

    @Override
    public TileCore getTileEntity(int metadata) {
        return new TileGateSocket();
    }
}
