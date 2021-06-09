package net.kunmc.lab.ametropia.handler;

import net.kunmc.lab.ametropia.command.AMCommands;
import net.kunmc.lab.ametropia.data.AmetropiaManager;
import net.kunmc.lab.ametropia.packet.PacketHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerHandler {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent e) {
        AMCommands.registerCommand(e.getDispatcher());
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent e) {
        AmetropiaManager manager = AmetropiaManager.getInstance();
        PacketHandler.sendSightChangePacket((ServerPlayerEntity) e.getPlayer(), manager.getPlayerState(e.getPlayer()).getType(), manager.getPlayerState(e.getPlayer()).getLevel());
    }
}
