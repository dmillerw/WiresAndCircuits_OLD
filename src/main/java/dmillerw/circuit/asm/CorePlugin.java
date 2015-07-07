package dmillerw.circuit.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @author dmillerw
 */
@IFMLLoadingPlugin.SortingIndex(1001)
public class CorePlugin implements IFMLLoadingPlugin {

    public static boolean obfuscated = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {"dmillerw.circuit.asm.TransformMinecraft"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        CorePlugin.obfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
