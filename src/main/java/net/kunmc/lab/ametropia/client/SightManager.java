package net.kunmc.lab.ametropia.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kunmc.lab.ametropia.client.renderer.AmetropiaRenderer;
import net.kunmc.lab.ametropia.client.renderer.HyperopiaRenderer;
import net.kunmc.lab.ametropia.client.renderer.MyopiaRenderer;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.kunmc.lab.ametropia.item.GlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;

public class SightManager {
    private static final SightManager INSTANCE = new SightManager();
    private static final Minecraft mc = Minecraft.getInstance();
    private AmetropiaType type = AmetropiaType.HYPEROPIA;
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

    public void setType(AmetropiaType type) {
        this.type = type;
    }

    public boolean isEnable() {
        return mc.player != null && type != AmetropiaType.NONE && !(mc.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof GlassesItem);
    }

    public void setLevel(float level) {
        this.level = MathHelper.clamp(level, 0, 1);
    }

    public void resize() {
        if (mc.level != null) {
            AmetropiaRenderer.getInstance().resized();
            HyperopiaRenderer.getInstance().resized();
        }
    }

    public void render(MatrixStack matrixStack, Matrix4f projectionMatrix) {
        if (isEnable()) {
            if (getType() == AmetropiaType.HYPEROPIA)
                HyperopiaRenderer.getInstance().doRender(matrixStack, projectionMatrix);
            else if (getType() == AmetropiaType.MYOPIA)
                MyopiaRenderer.getInstance().doRender(matrixStack, projectionMatrix);
        }
    }
}
