package net.kunmc.lab.ametropia.mixin.client;

import net.kunmc.lab.ametropia.client.renderer.AmetropiaRenderer;
import net.kunmc.lab.ametropia.client.renderer.HyperopiaRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Nullable
    public ClientWorld level;

    @Inject(method = "resizeDisplay", at = @At("HEAD"), cancellable = true)
    private void resizeDisplay(CallbackInfo ci) {
        if (level != null) {
            AmetropiaRenderer.getInstance().resized();
            HyperopiaRenderer.getInstance().resized();
        }
    }
}