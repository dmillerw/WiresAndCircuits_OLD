package dmillerw.circuit.api.util;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.api.tile.IStateHandler;
import dmillerw.circuit.core.connection.StateHandler;

import java.lang.reflect.Constructor;

/**
 * @author dmillerw
 */
public class APIHelper {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUFWXYZ";

    public static String getLetter(int index) {
        return Character.toString(ALPHABET.charAt(index % ALPHABET.length()));
    }

    public static IStateHandler getStateHandler(IConnectable connectable) {
        try {
            Class<StateHandler> cls = StateHandler.class;
            Constructor constructor = cls.getConstructor(IConnectable.class);
            return (IStateHandler) constructor.newInstance(connectable);
        } catch (Exception ex) {
            return null;
        }
    }
}
