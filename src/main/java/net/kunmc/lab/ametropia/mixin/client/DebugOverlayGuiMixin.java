package net.kunmc.lab.ametropia.mixin.client;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugOverlayGui.class)
public class DebugOverlayGuiMixin {
    @Inject(method = "getGameInformation", at = @At("RETURN"), cancellable = true)
    private void getGameInformation(CallbackInfoReturnable<List<String>> cir) {
        SightManager manager = SightManager.getInstance();
        cir.getReturnValue().add("Ametropia Info: [" + "Mode: " + manager.getType().getComponent().getString() + (manager.getType() == AmetropiaType.NONE ? "" : ", Level: " + manager.getLevel()) + "]");
    }
}
