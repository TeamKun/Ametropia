package net.kunmc.lab.ametropia.handler;

import net.kunmc.lab.ametropia.command.AMCommands;
import net.kunmc.lab.ametropia.data.AmetropiaManager;
import net.kunmc.lab.ametropia.item.AMItems;
import net.kunmc.lab.ametropia.packet.PacketHandler;
import net.kunmc.lab.ametropia.packet.SightChangeMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
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
        PacketHandler.sendSightChangePacket((ServerPlayerEntity) e.getPlayer(), SightChangeMessage.ChangeType.ALL,manager.getPlayerState(e.getPlayer()).getLevel(), manager.getPlayerState(e.getPlayer()).getRange());
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent e) {
        if (e.getEntityLiving().getItemBySlot(EquipmentSlotType.HEAD).getItem() == AMItems.GLASSES) {
            e.getEntityLiving().getItemBySlot(EquipmentSlotType.HEAD).hurtAndBreak(1, e.getEntityLiving(), n -> {
                n.broadcastBreakEvent(EquipmentSlotType.HEAD);
                if (!n.level.isClientSide())
                    n.level.playSound(null, n.getX(), n.getY(), n.getZ(), SoundEvents.GLASS_BREAK, SoundCategory.PLAYERS, 3, 1);
            });
        }
    }
}
