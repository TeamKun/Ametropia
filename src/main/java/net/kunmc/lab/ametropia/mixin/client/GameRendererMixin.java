package net.kunmc.lab.ametropia.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private LightTexture lightTexture;

    @Inject(method = "renderItemInHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getCameraEntity()Lnet/minecraft/entity/Entity;", ordinal = 0))
    private void renderItemInHand(MatrixStack p_228381_1_, ActiveRenderInfo p_228381_2_, float p_228381_3_, CallbackInfo ci) {
        if (!noInjected()) {
            this.lightTexture.turnOnLightLayer();
            this.lightTexture.turnOffLightLayer();
        }
    }

    private boolean noInjected() {
        boolean flag = this.minecraft.getCameraEntity() instanceof LivingEntity && ((LivingEntity) this.minecraft.getCameraEntity()).isSleeping();
        return this.minecraft.options.getCameraType().isFirstPerson() && !flag && !this.minecraft.options.hideGui && this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR;
    }

}
