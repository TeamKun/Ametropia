package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ClientHandler {


    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load e) {
        SightManager.getInstance().resize();
        SightManager manager = SightManager.getInstance();
        manager.setType(AmetropiaType.NONE);
        manager.setLevel(0);
    }
}
