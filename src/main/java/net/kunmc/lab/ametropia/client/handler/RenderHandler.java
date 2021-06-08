package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.SightManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        SightManager manager = SightManager.getInstance();
        manager.render(e.getMatrixStack(), e.getProjectionMatrix());
    }
}
