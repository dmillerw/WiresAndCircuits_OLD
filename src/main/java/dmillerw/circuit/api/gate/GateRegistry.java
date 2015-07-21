package dmillerw.circuit.api.gate;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Collection;

/**
 * @author dmillerw
 */
public class GateRegistry {

    public static GateRegistry INSTANCE;

    private final BiMap<String, Gate> gateMapping = HashBiMap.create();

    public void registerGate(String ident, Gate gate) {
        if (gateMapping.containsKey(ident))
            throw new IllegalArgumentException("Gate already exists with the name " + ident);

        gateMapping.put(ident, gate);
    }

    public Gate getGate(String ident) {
        return gateMapping.get(ident);
    }

    public String getGateName(Gate gate) {
        return gateMapping.inverse().get(gate);
    }

    public Collection<String> getAllGates() {
        return gateMapping.keySet();
    }
}
