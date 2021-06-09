package net.kunmc.lab.ametropia.packet;

import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.client.handler.SightChangeMessageHandler;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.kunmc.lab.ametropia.util.PlayerUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Ametropia.MODID, Ametropia.MODID + "_channel")).clientAcceptedVersions(a -> true).serverAcceptedVersions(a -> true).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    private static final ResourceLocation GLASSES_RECEP = new ResourceLocation(Ametropia.MODID, "recipes/glasses");
    private static int number = 0;

    public static void init() {
        INSTANCE.registerMessage(number++, SightChangeMessage.class, SightChangeMessage::encodeMessege, SightChangeMessage::decodeMessege, SightChangeMessageHandler::reversiveMessage);
    }

    public static void sendSightChangePacket(ServerPlayerEntity player, AmetropiaType type, float level) {
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SightChangeMessage(type, level));
        if (type != AmetropiaType.NONE) {
            PlayerUtils.grantAdvancement(GLASSES_RECEP, player);
        }
    }
}
