package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class MyopiaYShader extends BaseShader {
    private static final MyopiaYShader INSTANCE = new MyopiaYShader();
    private ShaderDefault positionUniform;

    private ShaderDefault level;
    private ShaderDefault range;

    public static MyopiaYShader getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "myopia_y");
    }

    @Override
    public void handleShaderLoad() {
        super.handleShaderLoad();
        positionUniform = shaderInstance.safeGetUniform("pos");

        level = shaderInstance.safeGetUniform("level");
        range = shaderInstance.safeGetUniform("range");
    }

    public void setPosition(Vector3d value) {
        positionUniform.set((float) value.x(), (float) value.y(), (float) value.z());
    }

    public void setLevel(float value) {
        level.set(value);
    }

    public void setRange(float value) {
        this.range.set(value);
    }
}
