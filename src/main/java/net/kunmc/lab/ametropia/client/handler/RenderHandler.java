package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.SightManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {


    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        SightManager manager = SightManager.getInstance();
        manager.render(e.getMatrixStack(), e.getProjectionMatrix(), e.getPartialTicks());
        manager.resizeTick();
    }
}
