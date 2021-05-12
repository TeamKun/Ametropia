package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class AmetropiaShader extends BaseShader {
    private static final AmetropiaShader INSTANCE = new AmetropiaShader();
    private ShaderDefault positionUniform;
    private ShaderDefault eyePositionUniform;

    public static AmetropiaShader getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "test");
    }

    @Override
    public void handleShaderLoad() {
        super.handleShaderLoad();
        positionUniform = shaderInstance.safeGetUniform("pos");
        eyePositionUniform = shaderInstance.safeGetUniform("eyePos");
    }

    public void setPosition(Vector3d value) {
        positionUniform.set((float) value.x(), (float) value.y(), (float) value.z());
    }

    public void setEyePosition(Vector3d value) {
        eyePositionUniform.set((float) value.x(), (float) value.y(), (float) value.z());
    }
}
