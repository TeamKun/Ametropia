package net.kunmc.lab.ametropia.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public class AMCommands {
    public static void registerCommand(CommandDispatcher<CommandSource> d) {
        SightCommand.register(d);
    }
}
