package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.shader.MyopiaShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class MyopiaRenderer extends ShaderBaseRenderer<MyopiaShader> {
    private static final MyopiaRenderer INSTANCE = new MyopiaRenderer();
    private static final Minecraft mc = Minecraft.getInstance();

    public static MyopiaRenderer getInstance() {

        return INSTANCE;
    }

    @Override
    public MyopiaShader getShader() {
        return MyopiaShader.getInstance();
    }

    @Override
    public void setter(Framebuffer framebuffer, float parTick) {
        getShader().setPosition(mc.player.getEyePosition(parTick));

        getShader().setLevel(-SightManager.getInstance().getDioptreLevel());
    }
}


