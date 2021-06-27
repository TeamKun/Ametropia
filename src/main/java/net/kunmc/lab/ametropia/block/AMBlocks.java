package net.kunmc.lab.ametropia.block;

import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.item.AMItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AMBlocks {
    public static List<Block> MOD_BLOCKS = new ArrayList<>();
    public static List<Item> MOD_ITEMS = new ArrayList<>();
    public static final Block SIGHT_TEST_CHART = register("sight_test_chart", new SightTestChartBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.0F, 1.0f)));

    private static Block register(String name, Block block) {
        return register(name, block, n -> new BlockItem(n, new Item.Properties().tab(AMItemGroup.TAB_AMETROPIA)));
    }

    private static Block register(String name, Block block, Function<Block, Item> item) {
        MOD_BLOCKS.add(block.setRegistryName(Ametropia.MODID, name));
        MOD_ITEMS.add(item.apply(block).setRegistryName(Ametropia.MODID, name));
        return block;
    }
}
