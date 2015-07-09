package dmillerw.circuit.util;

import dmillerw.circuit.api.tile.IConnectable;
import dmillerw.circuit.core.connection.Connection;
import dmillerw.circuit.core.connection.ConnectionHandler;
import dmillerw.circuit.core.connection.UpdateHandler;

/**
 * @author dmillerw
 */
public class ConnectionUtil {

    /**
     * Tells the game to update all the connections that are linked to the specified block and output port
     * Setting the output port to -1 will update ALL ports
     */
    public static void sendConnectableUpdate(IConnectable connectable, int port) {
        if (port == -1) {
            for (Connection connection : ConnectionHandler.INSTANCE.get(connectable)) {
                UpdateHandler.INSTANCE.queueUpdate(
                        connectable.getWorld(),
                        connection.target,
                        connection.targetInputPort,
                        connectable.getOutput(connection.selfOutputPort));
            }
        } else {
            for (Connection connection : ConnectionHandler.INSTANCE.get(connectable)) {
                if (connection.selfOutputPort == port) {
                    UpdateHandler.INSTANCE.queueUpdate(
                            connectable.getWorld(),
                            connection.target,
                            connection.targetInputPort,
                            connectable.getOutput(port));
                }
            }
        }
    }
}
