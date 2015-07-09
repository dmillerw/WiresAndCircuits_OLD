package dmillerw.circuit.network.packet.core;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * @author dmillerw
 */
public abstract class BaseHandler<P extends IMessage> implements IMessageHandler<P, IMessage> {

    @Override
    public IMessage onMessage(P message, MessageContext ctx) {
        handle(message, ctx);
        return null;
    }

    public abstract void handle(P message, MessageContext context);
}
