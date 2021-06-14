package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.shader.HyperopiaShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class HyperopiaRenderer extends ShaderBaseRenderer<HyperopiaShader> {
    private static final HyperopiaRenderer INSTANCE = new HyperopiaRenderer();
    private static final Minecraft mc = Minecraft.getInstance();

    public static HyperopiaRenderer getInstance() {

        return INSTANCE;
    }

    @Override
    public HyperopiaShader getShader() {
        return HyperopiaShader.getInstance();
    }

    @Override
    public void setter(Framebuffer framebuffer, float parTick) {
        getShader().setPosition(mc.player.getEyePosition(parTick));

        getShader().setLevel(SightManager.getInstance().getDioptreLevel());
    }
}
