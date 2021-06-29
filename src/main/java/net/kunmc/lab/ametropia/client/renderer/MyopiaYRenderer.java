package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.shader.MyopiaYShader;
import net.minecraft.client.Minecraft;

public class MyopiaYRenderer extends SightBaseRenderer<MyopiaYShader> {
    private static final MyopiaYRenderer INSTANCE = new MyopiaYRenderer();

    public static MyopiaYRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public MyopiaYShader getShader() {
        return MyopiaYShader.getInstance();
    }

    @Override
    public boolean isNegative() {
        return true;
    }
}
