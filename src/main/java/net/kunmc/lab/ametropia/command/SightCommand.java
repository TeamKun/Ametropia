package net.kunmc.lab.ametropia.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.kunmc.lab.ametropia.packet.PacketHandler;
import net.kunmc.lab.ametropia.packet.SightChangeMessage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.fml.network.PacketDistributor;

public class SightCommand {
    public static void register(CommandDispatcher<CommandSource> d) {
        d.register(Commands.literal(Ametropia.MODID).requires((source) -> source.hasPermission(2)).then(Commands.literal("level").then(Commands.argument("value", FloatArgumentType.floatArg()).executes((context) -> setClientValue(context.getSource(), AmetropiaType.HYPEROPIA, FloatArgumentType.getFloat(context, "value"))))));
      /*  d.register(Commands.literal(Ametropia.MODID).requires((source) -> source.hasPermission(2)).then(Commands.literal("set")
                .then(Commands.literal("focus").then(Commands.argument("value", FloatArgumentType.floatArg()).executes((context) -> setClientValue(context.getSource(), SightChangeMessage.Type.FOCUS_UNIFORM, FloatArgumentType.getFloat(context, "value")))))
                .then(Commands.literal("difference").then(Commands.argument("value", FloatArgumentType.floatArg()).executes((context) -> setClientValue(context.getSource(), SightChangeMessage.Type.DIFFERENCE_UNIFORM, FloatArgumentType.getFloat(context, "value")))))
                .then(Commands.literal("ignore").then(Commands.argument("value", FloatArgumentType.floatArg()).executes((context) -> setClientValue(context.getSource(), SightChangeMessage.Type.IGNORE_DIST, FloatArgumentType.getFloat(context, "value")))))
                .then(Commands.literal("range").then(Commands.argument("value", FloatArgumentType.floatArg()).executes((context) -> setClientValue(context.getSource(), SightChangeMessage.Type.RANGE_UNIFORM, FloatArgumentType.getFloat(context, "value")))))));*/
    }

    private static int setClientValue(CommandSource src, AmetropiaType type, float level) {
        if (type != null)
            PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new SightChangeMessage(type, level));

        return 1;
    }

}
