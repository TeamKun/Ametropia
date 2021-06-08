package net.kunmc.lab.ametropia.handler;

import net.kunmc.lab.ametropia.command.AMCommands;
import net.kunmc.lab.ametropia.data.AmetropiaManager;
import net.kunmc.lab.ametropia.packet.PacketHandler;
import net.kunmc.lab.ametropia.packet.SightChangeMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class ServerHandler {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent e) {
        AMCommands.registerCommand(e.getDispatcher());
    }

    @SubscribeEvent
    public static void onLoad(WorldEvent.Load e) {
        if (!e.getWorld().isClientSide()) {
            AmetropiaManager.getInstance().load(((ServerWorld) e.getWorld()).getServer().getWorldPath(FolderName.ROOT).resolve("data").toFile());
        }
    }

    @SubscribeEvent
    public static void onUnload(WorldEvent.Unload e) {
        if (!e.getWorld().isClientSide()) {
            AmetropiaManager.getInstance().unload();
        }
    }

    @SubscribeEvent
    public static void onSave(WorldEvent.Save e) {
        if (!e.getWorld().isClientSide()) {
            AmetropiaManager.getInstance().saveFile(((ServerWorld) e.getWorld()).getServer().getWorldPath(FolderName.ROOT).resolve("data").toFile());
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent e) {
        AmetropiaManager manager = AmetropiaManager.getInstance();
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) e.getPlayer()), new SightChangeMessage(manager.getPlayerState(e.getPlayer()).getType(), manager.getPlayerState(e.getPlayer()).getLevel()));
    }
}
