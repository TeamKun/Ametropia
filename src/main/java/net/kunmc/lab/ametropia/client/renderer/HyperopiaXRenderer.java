package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.shader.HyperopiaXShader;

public class HyperopiaXRenderer extends SightBaseRenderer<HyperopiaXShader> {
    private static final HyperopiaXRenderer INSTANCE = new HyperopiaXRenderer();

    public static HyperopiaXRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public HyperopiaXShader getShader() {
        return HyperopiaXShader.getInstance();
    }

    @Override
    public boolean isNegative() {
        return false;
    }
}
