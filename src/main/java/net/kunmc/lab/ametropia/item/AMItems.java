package net.kunmc.lab.ametropia.item;

import net.kunmc.lab.ametropia.Ametropia;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AMItems {
    public static List<Item> MOD_ITEMS = new ArrayList<>();
    public static final Item GLASSES = register("glasses", new GlassesItem(new Item.Properties().tab(AMItemGroup.TAB_AMETROPIA).durability(10)));

    private static Item register(String name, Item item) {
        MOD_ITEMS.add(item.setRegistryName(Ametropia.MODID, name));
        return item;
    }
}
