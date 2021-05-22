package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.client.shader.AmetropiaShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.math.vector.Vector3d;

public class AmetropiaRenderer extends ShaderBaseRenderer<AmetropiaShader> {
    private static final AmetropiaRenderer INSTANCE = new AmetropiaRenderer();
    private static final Minecraft mc = Minecraft.getInstance();

    public static AmetropiaRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public AmetropiaShader getShader() {
        return AmetropiaShader.getInstance();
    }

    @Override
    public void setter(Framebuffer framebuffer) {
        Vector3d position = mc.gameRenderer.getMainCamera().getPosition();
        getShader().setPosition(position);

        SightManager manager = SightManager.getInstance();


        float level = manager.getLevel();

        getShader().setFocus(0);
        getShader().setRange(0);
        getShader().setDifference(0);
        getShader().setIgnoreDist(0);
    }
}
