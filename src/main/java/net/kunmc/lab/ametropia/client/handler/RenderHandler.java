package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.renderer.AmetropiaRenderer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        AmetropiaRenderer.getInstance().onRender(e);
    }
}
