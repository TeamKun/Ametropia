package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.packet.SightChangeMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SightChangeMessageHandler {
    public static void reversiveMessage(SightChangeMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);

        SightManager manager = SightManager.getInstance();
        if (message.level >= 0)
            manager.setLevel(message.level);
        manager.setType(message.type);
    }
}
