package net.kunmc.lab.ametropia.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.client.renderer.HyperopiaRenderer;
import net.kunmc.lab.ametropia.client.renderer.MyopiaRenderer;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OutlineLayerBuffer;endOutlineBatch()V"))
    private void renderLevel(MatrixStack matrixStack, float p_228426_2_, long p_228426_3_, boolean p_228426_5_, ActiveRenderInfo p_228426_6_, GameRenderer p_228426_7_, LightTexture p_228426_8_, Matrix4f p_228426_9_, CallbackInfo ci) {
        SightManager manager = SightManager.getInstance();
        if (manager.isEnable()) {
            if (manager.getType() == AmetropiaType.HYPEROPIA)
                HyperopiaRenderer.getInstance().doRender(matrixStack, p_228426_9_);
            else if (manager.getType() == AmetropiaType.MYOPIA)
                MyopiaRenderer.getInstance().doRender(matrixStack, p_228426_9_);
        }
    }
}
