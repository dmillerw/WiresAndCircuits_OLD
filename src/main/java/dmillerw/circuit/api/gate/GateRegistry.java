package dmillerw.circuit.api.gate;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author dmillerw
 */
public class GateRegistry {

    public static final Map<String, IGate> gateRegistry = Maps.newHashMap();

    public static void registerGate(String identifier, IGate gate) {
        gateRegistry.put(identifier, gate); // TODO sanity
    }

    public static IGate getGate(String identifier) {
        return gateRegistry.get(identifier);
    }
}
