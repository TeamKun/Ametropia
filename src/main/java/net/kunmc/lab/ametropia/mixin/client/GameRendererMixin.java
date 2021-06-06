package net.kunmc.lab.ametropia.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    private final ResourceLocation lightTextureLocation = new ResourceLocation("light_map");

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private LightTexture lightTexture;

    @Inject(method = "renderItemInHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getCameraEntity()Lnet/minecraft/entity/Entity;", ordinal = 0))
    private void renderItemInHand(MatrixStack p_228381_1_, ActiveRenderInfo p_228381_2_, float p_228381_3_, CallbackInfo ci) {
        if (!noInjected()) {
        //    this.lightTexture.turnOnLightLayer();
        //   RenderSystem.activeTexture(33986);
       //  RenderSystem.matrixMode(5890);
        /*       RenderSystem.loadIdentity();
            float f = 0.00390625F;
            RenderSystem.scalef(0.00390625F, 0.00390625F, 0.00390625F);
            RenderSystem.translatef(8.0F, 8.0F, 8.0F);
            RenderSystem.matrixMode(5888);
            this.minecraft.getTextureManager().bind(this.lightTextureLocation);
            RenderSystem.texParameter(3553, 10241, 9729);
            RenderSystem.texParameter(3553, 10240, 9729);
            RenderSystem.texParameter(3553, 10242, 10496);
            RenderSystem.texParameter(3553, 10243, 10496);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableTexture();
            RenderSystem.activeTexture(33984);

            RenderSystem.activeTexture(33986);*/
          //  RenderSystem.disableTexture();
            RenderSystem.activeTexture(33984);
        }
    }

    private boolean noInjected() {
        boolean flag = this.minecraft.getCameraEntity() instanceof LivingEntity && ((LivingEntity) this.minecraft.getCameraEntity()).isSleeping();
        return this.minecraft.options.getCameraType().isFirstPerson() && !flag && !this.minecraft.options.hideGui && this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR;
    }

}
