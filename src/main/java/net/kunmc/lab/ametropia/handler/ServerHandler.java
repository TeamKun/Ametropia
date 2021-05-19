package net.kunmc.lab.ametropia.handler;

import net.kunmc.lab.ametropia.command.AMCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerHandler {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent e) {
        AMCommands.registerCommand(e.getDispatcher());
    }
}
