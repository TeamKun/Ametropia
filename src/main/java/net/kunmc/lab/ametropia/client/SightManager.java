package net.kunmc.lab.ametropia.client;

import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.kunmc.lab.ametropia.item.GlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.MathHelper;

public class SightManager {
    private static final SightManager INSTANCE = new SightManager();
    private static final Minecraft mc = Minecraft.getInstance();
    private AmetropiaType type = AmetropiaType.MYOPIA;
    private float level;


    public static SightManager getInstance() {
        return INSTANCE;
    }

    public float getLevel() {
        return level;
    }

    public AmetropiaType getType() {
        return type;
    }

    public boolean isEnable() {
        return mc.player != null && type != AmetropiaType.NONE && !(mc.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof GlassesItem);
    }

    public void setLevel(float level) {
        this.level = MathHelper.clamp(level,0,1);
    }
}
