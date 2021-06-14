package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.packet.SightChangeMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SightChangeMessageHandler {
    public static void reversiveMessage(SightChangeMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        SightManager manager = SightManager.getInstance();
        switch (message.changeType) {
            case ALL:
                manager.setLevel(message.level);
                manager.setRange(message.range);
                break;
            case LEVEL:
                manager.setLevel(message.level);
                break;
            case RANGE:
                if (message.range >= 0)
                    manager.setRange(message.range);
                break;
        }
    }
}
