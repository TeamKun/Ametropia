package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.shader.SightBaseShader;
import net.kunmc.lab.ametropia.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public abstract class SightBaseRenderer<T extends SightBaseShader> extends ShaderBaseRenderer<T> {
    private static final Minecraft mc = Minecraft.getInstance();

    @Override
    public void setter(Framebuffer framebuffer, float parTick) {
        getShader().setPosition(mc.player.getEyePosition(parTick));
        SightManager manager = SightManager.getInstance();
        getShader().setLevel(manager.getDioptreLevel() * (isNegative() ? -1 : 1));
        getShader().setRange(manager.getRange());
        getShader().setRenderDistance(RenderUtil.getRenderDistance());
    }

    abstract public boolean isNegative();

}
