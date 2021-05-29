package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.client.shader.MyopiaShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.math.vector.Vector3d;

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
    public void setter(Framebuffer framebuffer) {
        Vector3d position = mc.gameRenderer.getMainCamera().getPosition();
        getShader().setPosition(position);

        getShader().setLevel(SightManager.getInstance().getLevel());
    }
}


