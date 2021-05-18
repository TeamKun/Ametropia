package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {
    @SubscribeEvent
    public static void onTextureStith(TextureStitchEvent.Pre e) {
        if (e.getMap().location().equals(PlayerContainer.BLOCK_ATLAS)) {
            e.addSprite(new ResourceLocation(Ametropia.MODID, "item/glasses"));
        }
    }
}
