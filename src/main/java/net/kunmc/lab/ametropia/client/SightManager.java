package net.kunmc.lab.ametropia.client;

import net.kunmc.lab.ametropia.item.GlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;

public class SightManager {
    private static final SightManager INSTANCE = new SightManager();
    private static final Minecraft mc = Minecraft.getInstance();

    public static SightManager getInstance() {
        return INSTANCE;
    }

    public boolean isEnable() {
        return mc.player != null && !(mc.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof GlassesItem);
    }

}
