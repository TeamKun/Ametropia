package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ClientHandler {
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load e) {
        SightManager.getInstance().resize();
    }
}
