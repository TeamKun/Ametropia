package net.kunmc.lab.ametropia.client.shader;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.util.ResourceLocation;

public class MyopiaXShader extends SightBaseShader {
    private static final MyopiaXShader INSTANCE = new MyopiaXShader();

    public static MyopiaXShader getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getShaderLocation() {
        return new ResourceLocation(Ametropia.MODID, "myopia_x");
    }
}
