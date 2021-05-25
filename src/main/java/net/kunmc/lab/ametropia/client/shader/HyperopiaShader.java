package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class HyperopiaShader extends BaseShader {
    private static final HyperopiaShader INSTANCE = new HyperopiaShader();
    private ShaderDefault positionUniform;

    private ShaderDefault focusUniform;
    private ShaderDefault rangeUniform;
    private ShaderDefault differenceUniform;
    private ShaderDefault ignoreDist;
    private ShaderDefault level;

    public static HyperopiaShader getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "hyperopia");
    }

    @Override
    public void handleShaderLoad() {
        super.handleShaderLoad();
        positionUniform = shaderInstance.safeGetUniform("pos");

        focusUniform = shaderInstance.safeGetUniform("focus");
        rangeUniform = shaderInstance.safeGetUniform("range");
        differenceUniform = shaderInstance.safeGetUniform("difference");
        ignoreDist = shaderInstance.safeGetUniform("ignoreDist");
        level = shaderInstance.safeGetUniform("level");
    }

    public void setPosition(Vector3d value) {
        positionUniform.set((float) value.x(), (float) value.y(), (float) value.z());
    }


    public void setFocus(float value) {
        focusUniform.set(value);
    }

    public void setRange(float value) {
        rangeUniform.set(value);
    }

    public void setDifference(float value) {
        differenceUniform.set(value);
    }

    public void setIgnoreDist(float value) {
        ignoreDist.set(value);
    }

    public void setLevel(float value) {
        level.set(value);
    }
}
