package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.shader.MyopiaYShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class MyopiaYRenderer extends ShaderBaseRenderer<MyopiaYShader> {
    private static final MyopiaYRenderer INSTANCE = new MyopiaYRenderer();
    private static final Minecraft mc = Minecraft.getInstance();

    public static MyopiaYRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public MyopiaYShader getShader() {
        return MyopiaYShader.getInstance();
    }

    @Override
    public void setter(Framebuffer framebuffer, float parTick) {
        getShader().setPosition(mc.player.getEyePosition(parTick));
        SightManager manager = SightManager.getInstance();
        getShader().setLevel(-manager.getDioptreLevel());
        getShader().setRange(manager.getRange());
    }
}
