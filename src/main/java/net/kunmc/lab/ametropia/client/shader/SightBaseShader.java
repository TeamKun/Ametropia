package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class SightBaseShader extends BaseShader {
    private final String name;
    private ShaderDefault positionUniform;

    private ShaderDefault level;
    private ShaderDefault range;
    private ShaderDefault renderDistance;

    public SightBaseShader(String name) {
        this.name = name;
    }

    @Override
    public void handleShaderLoad() {
        super.handleShaderLoad();
        positionUniform = shaderInstance.safeGetUniform("pos");

        level = shaderInstance.safeGetUniform("level");
        range = shaderInstance.safeGetUniform("range");
        renderDistance = shaderInstance.safeGetUniform("renderDistance");
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

    public void setRenderDistance(float value) {
        this.renderDistance.set(value);
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, name);
    }
}
