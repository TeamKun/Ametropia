package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.client.renderer.AmetropiaRenderer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        if (SightManager.getInstance().isEnable())
            AmetropiaRenderer.getInstance().onRender(e);
    }
}
