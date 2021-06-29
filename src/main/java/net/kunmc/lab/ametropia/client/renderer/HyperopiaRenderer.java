package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.shader.HyperopiaShader;
import net.kunmc.lab.ametropia.client.shader.HyperopiaXShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class HyperopiaRenderer extends SightBaseRenderer<HyperopiaShader> {
    private static final HyperopiaRenderer INSTANCE = new HyperopiaRenderer();

    public static HyperopiaRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public HyperopiaShader getShader() {
        return HyperopiaShader.getInstance();
    }

    @Override
    public boolean isNegative() {
        return false;
    }
}