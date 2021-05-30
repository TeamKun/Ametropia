package net.kunmc.lab.ametropia.mixin.client;

import net.kunmc.lab.ametropia.client.SightManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "resizeDisplay", at = @At("HEAD"), cancellable = true)
    private void resizeDisplay(CallbackInfo ci) {
        SightManager.getInstance().resize();
    }
}