package net.kunmc.lab.ametropia.client;

import net.kunmc.lab.ametropia.item.GlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;

public class SightManager {
    private static final SightManager INSTANCE = new SightManager();
    private static final Minecraft mc = Minecraft.getInstance();
    public float focusUniform;
    public float rangeUniform;
    public float differenceUniform;
    public float ignoreDist;

    public static SightManager getInstance() {
        return INSTANCE;
    }

    public boolean isEnable() {
        return mc.player != null && !(mc.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof GlassesItem);
    }


    public void setDifferenceUniform(float differenceUniform) {
        this.differenceUniform = differenceUniform;
    }

    public void setFocusUniform(float focusUniform) {
        this.focusUniform = focusUniform;
    }

    public void setIgnoreDist(float ignoreDist) {
        this.ignoreDist = ignoreDist;
    }

    public void setRangeUniform(float rangeUniform) {
        this.rangeUniform = rangeUniform;
    }

    public float getFocusUniform() {
        return focusUniform;
    }

    public float getRangeUniform() {
        return rangeUniform;
    }

    public float getDifferenceUniform() {
        return differenceUniform;
    }

    public float getIgnoreDist() {
        return ignoreDist;
    }


}
