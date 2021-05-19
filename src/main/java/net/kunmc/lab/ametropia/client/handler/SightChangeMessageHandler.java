package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.packet.SightChangeMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SightChangeMessageHandler {
    public static void reversiveMessage(SightChangeMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        SightManager manager = SightManager.getInstance();

        System.out.println(message.type.getSerializedName() + ":" + message.value);

        switch (message.type) {
            case IGNORE_DIST:
                manager.setIgnoreDist(message.value);
                break;
            case FOCUS_UNIFORM:
                manager.setFocusUniform(message.value);
                break;
            case DIFFERENCE_UNIFORM:
                manager.setDifferenceUniform(message.value);
                break;
            case RANGE_UNIFORM:
                manager.setRangeUniform(message.value);
        }

    }
}
