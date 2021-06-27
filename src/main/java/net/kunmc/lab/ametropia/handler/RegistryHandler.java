package net.kunmc.lab.ametropia.handler;

import net.kunmc.lab.ametropia.block.AMBlocks;
import net.kunmc.lab.ametropia.item.AMItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {
        AMBlocks.MOD_ITEMS.forEach(n -> e.getRegistry().register(n));
        AMItems.MOD_ITEMS.forEach(n -> e.getRegistry().register(n));
    }

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> e) {
        AMBlocks.MOD_BLOCKS.forEach(n -> e.getRegistry().register(n));
    }
}
