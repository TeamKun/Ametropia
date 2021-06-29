package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.shader.HyperopiaYShader;

public class HyperopiaYRenderer extends SightBaseRenderer<HyperopiaYShader> {
    private static final HyperopiaYRenderer INSTANCE = new HyperopiaYRenderer();

    public static HyperopiaYRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public HyperopiaYShader getShader() {
        return HyperopiaYShader.getInstance();
    }

    @Override
    public boolean isNegative() {
        return false;
    }
}
