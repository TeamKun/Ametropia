package net.kunmc.lab.ametropia.client.data;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kunmc.lab.ametropia.client.renderer.HyperopiaRenderer;
import net.kunmc.lab.ametropia.client.renderer.MyopiaRenderer;
import net.kunmc.lab.ametropia.client.renderer.MyopiaXRenderer;
import net.kunmc.lab.ametropia.client.renderer.MyopiaYRenderer;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.kunmc.lab.ametropia.item.GlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Matrix4f;

public class SightManager {
    private static final SightManager INSTANCE = new SightManager();
    private static final Minecraft mc = Minecraft.getInstance();
    private float level;
    private float range;
    private long lastResize;

    public static SightManager getInstance() {
        return INSTANCE;
    }

    public float getLevel() {
        return level;
    }


    public boolean isEnable() {
        return mc.player != null && level != 0;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getRange() {
        return range;
    }

    public AmetropiaType getTypeByLevel(float level) {
        return level < 0 ? AmetropiaType.MYOPIA : level > 0 ? AmetropiaType.HYPEROPIA : AmetropiaType.NONE;
    }

    public AmetropiaType getType() {
        return getTypeByLevel(getDioptreLevel());
    }

    public void resize() {
        if (mc.level != null) {
            MyopiaRenderer.getInstance().resized();
            //  HyperopiaRenderer.getInstance().resized();

            MyopiaXRenderer.getInstance().resized();
            MyopiaYRenderer.getInstance().resized();
        }
        lastResize = System.currentTimeMillis();
    }

    public void render(MatrixStack matrixStack, Matrix4f projectionMatrix, float parTick) {
        if (isEnable()) {
            if (getType() == AmetropiaType.HYPEROPIA)
                HyperopiaRenderer.getInstance().doRender(matrixStack, projectionMatrix, parTick);
            else if (getType() == AmetropiaType.MYOPIA) {
                MyopiaYRenderer.getInstance().doRender(matrixStack, projectionMatrix, parTick);
                MyopiaXRenderer.getInstance().doRender(matrixStack, projectionMatrix, parTick);
                //    MyopiaRenderer.getInstance().doRender(matrixStack, projectionMatrix, parTick);
            }
        }
    }

    public void resizeTick() {
        if (System.currentTimeMillis() - lastResize > 1000 * 60)
            resize();
    }

    public float getDioptreLevel() {
        return getLevel() + getDioptre();
    }

    public float getDioptre() {
        if (mc.player != null && mc.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof GlassesItem) {
            return GlassesItem.getDioptre(mc.player.getItemBySlot(EquipmentSlotType.HEAD));
        }
        return 0;
    }
}
