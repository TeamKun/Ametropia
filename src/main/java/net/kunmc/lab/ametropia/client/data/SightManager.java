package net.kunmc.lab.ametropia.client.data;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kunmc.lab.ametropia.client.renderer.AMShaderRenderers;
import net.kunmc.lab.ametropia.client.renderer.ShaderBaseRenderer;
import net.kunmc.lab.ametropia.client.shader.AMShaders;
import net.kunmc.lab.ametropia.client.shader.BaseShader;
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
        return mc.player != null && (getDioptreLevel() > 0.1f || getDioptreLevel() < -0.1f);
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
        if (mc.level != null)
            AMShaderRenderers.allDoing(ShaderBaseRenderer::resized);

        lastResize = System.currentTimeMillis();
    }

    public void render(MatrixStack matrixStack, Matrix4f projectionMatrix, float parTick) {
        if (isEnable()) {
            if (getType() == AmetropiaType.HYPEROPIA) {
                AMShaderRenderers.getHyperopiaYInstance().doRender(matrixStack, projectionMatrix, parTick);
                AMShaderRenderers.getHyperopiaXInstance().doRender(matrixStack, projectionMatrix, parTick);
            } else if (getType() == AmetropiaType.MYOPIA) {
                AMShaderRenderers.getMyopiaYInstance().doRender(matrixStack, projectionMatrix, parTick);
                AMShaderRenderers.getMyopiaXInstance().doRender(matrixStack, projectionMatrix, parTick);
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

    public void init() {
        AMShaders.allDoing(BaseShader::init);
    }

}
