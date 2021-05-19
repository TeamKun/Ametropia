package net.kunmc.lab.ametropia.packet;

import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.client.handler.SightChangeMessageHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Ametropia.MODID, Ametropia.MODID + "_channel")).clientAcceptedVersions(a -> true).serverAcceptedVersions(a -> true).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    private static int number = 0;

    public static void init() {
        INSTANCE.registerMessage(number++, SightChangeMessage.class, SightChangeMessage::encodeMessege, SightChangeMessage::decodeMessege, SightChangeMessageHandler::reversiveMessage);
    }
}
