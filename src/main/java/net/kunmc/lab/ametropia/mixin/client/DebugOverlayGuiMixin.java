package net.kunmc.lab.ametropia.mixin.client;

import net.kunmc.lab.ametropia.client.data.SightManager;
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
        String levelInf = "Dioptre: " + manager.getDioptreLevel() + (manager.getLevel() != manager.getDioptreLevel() ? "[" + manager.getLevel() + (manager.getDioptre() >= 0 ? "+" : "") + manager.getDioptre() + "]" : "") + "(" + manager.getType().getComponent().getString() + ")";
        cir.getReturnValue().add("Ametropia Info: [" + levelInf + ", Range: " + manager.getRange() + "]");
    }
}
