package net.kunmc.lab.ametropia.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class PlayerUtils {
    public static void grantAdvancement(ResourceLocation rl, ServerPlayerEntity spl) {

        Advancement advancement = spl.getServer().getAdvancements().getAdvancement(rl);
        AdvancementProgress advancementprogress = spl.getAdvancements().getOrStartProgress(advancement);

        if (advancementprogress.isDone())
            return;

        for (String s : advancementprogress.getRemainingCriteria()) {
            spl.getAdvancements().award(advancement, s);
        }
    }
}
