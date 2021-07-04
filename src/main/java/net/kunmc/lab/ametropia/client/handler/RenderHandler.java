package net.kunmc.lab.ametropia.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kunmc.lab.ametropia.client.data.SightManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        SightManager manager = SightManager.getInstance();
        manager.render(e.getMatrixStack(), e.getProjectionMatrix(), e.getPartialTicks());
        manager.resizeTick();
        if (manager.isEnable() && !noInjected())
            RenderSystem.activeTexture(33984);

    }

    private static boolean noInjected() {
        boolean flag = mc.getCameraEntity() instanceof LivingEntity && ((LivingEntity) mc.getCameraEntity()).isSleeping();
        return mc.options.getCameraType().isFirstPerson() && !flag && !mc.options.hideGui && mc.gameMode.getPlayerMode() != GameType.SPECTATOR;
    }
}
