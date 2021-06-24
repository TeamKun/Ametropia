package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.shader.MyopiaXShader;
import net.kunmc.lab.ametropia.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class MyopiaXRenderer extends ShaderBaseRenderer<MyopiaXShader> {
    private static final MyopiaXRenderer INSTANCE = new MyopiaXRenderer();
    private static final Minecraft mc = Minecraft.getInstance();

    public static MyopiaXRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public MyopiaXShader getShader() {
        return MyopiaXShader.getInstance();
    }

    @Override
    public void setter(Framebuffer framebuffer, float parTick) {
        getShader().setPosition(mc.player.getEyePosition(parTick));
        SightManager manager = SightManager.getInstance();
        getShader().setLevel(-manager.getDioptreLevel());
        getShader().setRange(manager.getRange());
        getShader().setRenderDistance(RenderUtil.getRenderDistance());
    }
}
