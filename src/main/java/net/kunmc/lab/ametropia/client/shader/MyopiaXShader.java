package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class MyopiaXShader extends BaseShader {
    private static final MyopiaXShader INSTANCE = new MyopiaXShader();
    private ShaderDefault positionUniform;

    private ShaderDefault level;

    public static MyopiaXShader getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "myopia_x");
    }

    @Override
    public void handleShaderLoad() {
        super.handleShaderLoad();
        positionUniform = shaderInstance.safeGetUniform("pos");

        level = shaderInstance.safeGetUniform("level");
    }

    public void setPosition(Vector3d value) {
        positionUniform.set((float) value.x(), (float) value.y(), (float) value.z());
    }

    public void setLevel(float value) {
        level.set(value);
    }
}
