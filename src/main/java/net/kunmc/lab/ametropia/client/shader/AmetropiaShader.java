package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class AmetropiaShader extends BaseShader {
    private static final AmetropiaShader INSTANCE = new AmetropiaShader();
    private ShaderDefault positionUniform;
    private ShaderDefault eyePositionUniform;

    private ShaderDefault focusUniform;
    private ShaderDefault rangeUniform;
    private ShaderDefault differenceUniform;

    public static AmetropiaShader getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "sight");
    }

    @Override
    public void handleShaderLoad() {
        super.handleShaderLoad();
        positionUniform = shaderInstance.safeGetUniform("pos");
        eyePositionUniform = shaderInstance.safeGetUniform("eyePos");

        focusUniform = shaderInstance.safeGetUniform("focus");
        rangeUniform = shaderInstance.safeGetUniform("range");
        differenceUniform = shaderInstance.safeGetUniform("difference");
    }

    public void setPosition(Vector3d value) {
        positionUniform.set((float) value.x(), (float) value.y(), (float) value.z());
    }

    public void setEyePosition(Vector3d value) {
        eyePositionUniform.set((float) value.x(), (float) value.y(), (float) value.z());
    }

    public void setPosition(Vector3d value) {
        
        
    }
}
