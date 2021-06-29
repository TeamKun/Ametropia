package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.shader.MyopiaXShader;

public class MyopiaXRenderer extends SightBaseRenderer<MyopiaXShader> {
    private static final MyopiaXRenderer INSTANCE = new MyopiaXRenderer();

    public static MyopiaXRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public MyopiaXShader getShader() {
        return MyopiaXShader.getInstance();
    }

    @Override
    public boolean isNegative() {
        return true;
    }
}
