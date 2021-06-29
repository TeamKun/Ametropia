package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.util.ResourceLocation;

public class HyperopiaXShader extends SightBaseShader {
    private static final HyperopiaXShader INSTANCE = new HyperopiaXShader();

    public static HyperopiaXShader getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "hyperopia_x");
    }
}
