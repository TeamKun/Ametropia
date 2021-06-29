package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.util.ResourceLocation;

public class HyperopiaYShader extends SightBaseShader{
    private static final HyperopiaYShader INSTANCE = new HyperopiaYShader();

    public static HyperopiaYShader getInstance() {
        return INSTANCE;
    }
    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "hyperopia_y");
    }
}
