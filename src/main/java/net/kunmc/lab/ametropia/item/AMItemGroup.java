package net.kunmc.lab.ametropia.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AMItemGroup {
    public static final ItemGroup TAB_AMETROPIA = new ItemGroup("ametropia") {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(AMItems.GLASSES);
        }
    };
}
