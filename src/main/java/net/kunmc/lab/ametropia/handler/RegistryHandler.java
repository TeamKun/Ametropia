package net.kunmc.lab.ametropia.handler;

import net.kunmc.lab.ametropia.item.ATItems;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {
        ATItems.MOD_ITEMS.forEach(n -> e.getRegistry().register(n));
    }
}