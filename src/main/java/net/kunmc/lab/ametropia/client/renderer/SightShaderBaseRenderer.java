package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.shader.SightBaseShader;
import net.kunmc.lab.ametropia.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class SightShaderBaseRenderer extends ShaderBaseRenderer<SightBaseShader> {
    private static final Minecraft mc = Minecraft.getInstance();
    private final SightBaseShader shader;
    private final boolean negative;

    public SightShaderBaseRenderer(SightBaseShader shader, boolean negative) {
        this.shader = shader;
        this.negative = negative;
    }

    @Override
    public SightBaseShader getShader() {
        return shader;
    }

    @Override
    public void setter(Framebuffer framebuffer, float parTick) {
        getShader().setPosition(mc.player.getEyePosition(parTick));
        SightManager manager = SightManager.getInstance();
        getShader().setLevel(manager.getDioptreLevel() * (negative ? -1 : 1));
        getShader().setRange(manager.getRange());
        getShader().setRenderDistance(RenderUtil.getRenderDistance());
    }
}
