package net.kunmc.lab.ametropia.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.data.AmetropiaManager;
import net.kunmc.lab.ametropia.packet.PacketHandler;
import net.kunmc.lab.ametropia.packet.SightChangeMessage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Collection;

public class SightCommand {
    public static void register(CommandDispatcher<CommandSource> d) {
        d.register(Commands.literal(Ametropia.MODID).requires((source) -> source.hasPermission(2))
                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                        .then(Commands.literal("level").then(Commands.argument("value", FloatArgumentType.floatArg()).executes((context) -> setClientValue(context.getSource(), SightChangeMessage.ChangeType.LEVEL, FloatArgumentType.getFloat(context, "value"), 0, GameProfileArgument.getGameProfiles(context, "targets")))))));
      /*  d.register(Commands.literal(Ametropia.MODID).requires((source) -> source.hasPermission(2))
                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                        .then(Commands.literal("level").then(Commands.argument("value", FloatArgumentType.floatArg(0, 1)).executes((context) -> setClientValue(context.getSource(), null, FloatArgumentType.getFloat(context, "value"), GameProfileArgument.getGameProfiles(context, "targets")))))
                        .then(Commands.literal("type").then(Commands.literal("none").executes((context) -> setClientValue(context.getSource(), AmetropiaType.NONE, -1, GameProfileArgument.getGameProfiles(context, "targets"))))
                                .then(Commands.literal("hyperopia").executes((context) -> setClientValue(context.getSource(), AmetropiaType.HYPEROPIA, -1, GameProfileArgument.getGameProfiles(context, "targets"))))
                                .then(Commands.literal("myopia").executes((context) -> setClientValue(context.getSource(), AmetropiaType.MYOPIA, -1, GameProfileArgument.getGameProfiles(context, "targets")))))
                        .then(Commands.literal("random").executes((context) -> setRandomClientValue(context.getSource(), GameProfileArgument.getGameProfiles(context, "targets"))))));*/
    }

    private static int setClientValue(CommandSource src, SightChangeMessage.ChangeType type, float level, float range, Collection<GameProfile> targets) {
        int i = 0;
        GameProfile ls = null;
        AmetropiaManager manager = AmetropiaManager.getInstance();
        for (GameProfile target : targets) {
            ServerPlayerEntity serverplayerentity = src.getServer().getPlayerList().getPlayer(target.getId());

            switch (type) {
                case ALL:
                    manager.setPlayerStateLevel(serverplayerentity, level);
                    manager.setPlayerStateRange(serverplayerentity, range);
                    break;
                case LEVEL:
                    manager.setPlayerStateLevel(serverplayerentity, level);
                    break;
                case RANGE:
                    manager.setPlayerStateRange(serverplayerentity, range);
                    break;
            }

            PacketHandler.sendSightChangePacket(serverplayerentity, type, level, range);
            i++;
            ls = target;
        }


        return i;
    }

   /* private static int setClientValue(CommandSource src, AmetropiaType type, float level, Collection<GameProfile> targets) {
        int i = 0;

        boolean changeFlg = type != null;
        String cy = changeFlg ? "type" : "level";

        AmetropiaManager manager = AmetropiaManager.getInstance();
        GameProfile ls = null;
        for (GameProfile target : targets) {
            ServerPlayerEntity serverplayerentity = src.getServer().getPlayerList().getPlayer(target.getId());

            if (type == null)
                type = manager.getPlayerState(serverplayerentity).getType();
            else
                manager.setPlayerStateType(serverplayerentity, type);

            if (level >= 0)
                manager.setPlayerStateLevel(serverplayerentity, level);

            PacketHandler.sendSightChangePacket(serverplayerentity, type, level);
            i++;

            ls = target;
        }

        if (i == 1) {
            src.sendSuccess(new TranslationTextComponent("commands.sight." + cy + ".success.single", TextComponentUtils.getDisplayName(ls), changeFlg ? type.getComponent() : level), true);
        } else {
            src.sendSuccess(new TranslationTextComponent("commands.sight." + cy + ".success.multiple", i, changeFlg ? type.getComponent() : level), true);
        }

        return i;
    }

    private static int setRandomClientValue(CommandSource src, Collection<GameProfile> targets) {
        int i = 0;
        AmetropiaManager manager = AmetropiaManager.getInstance();

        GameProfile ls = null;
        for (GameProfile target : targets) {
            ServerPlayerEntity serverplayerentity = src.getServer().getPlayerList().getPlayer(target.getId());
            Random r = new Random();

            AmetropiaType type = r.nextBoolean() ? AmetropiaType.MYOPIA : AmetropiaType.HYPEROPIA;
            float level = r.nextFloat();
            manager.setPlayerStateType(serverplayerentity, type);
            manager.setPlayerStateLevel(serverplayerentity, level);

            PacketHandler.sendSightChangePacket(serverplayerentity, type, level);

            i++;
            ls = target;
        }
        if (i == 1) {
            src.sendSuccess(new TranslationTextComponent("commands.sight.random.success.single", TextComponentUtils.getDisplayName(ls)), true);
        } else {
            src.sendSuccess(new TranslationTextComponent("commands.sight.random.success.multiple", i), true);
        }
        return i;
    }
*/
}
