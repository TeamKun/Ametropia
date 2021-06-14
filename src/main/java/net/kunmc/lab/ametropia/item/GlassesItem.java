package net.kunmc.lab.ametropia.item;

import net.kunmc.lab.ametropia.client.renderer.item.GlassesRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GlassesItem extends Item {
    public GlassesItem(Properties properties) {
        super(properties.setISTER(() -> GlassesRenderer::new));
    }

    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstack);
        ItemStack itemstack1 = p_77659_2_.getItemBySlot(equipmentslottype);
        if (itemstack1.isEmpty()) {
            p_77659_2_.setItemSlot(equipmentslottype, itemstack.copy());
            itemstack.setCount(0);
            return ActionResult.sidedSuccess(itemstack, p_77659_1_.isClientSide());
        } else {
            return ActionResult.fail(itemstack);
        }
    }

    public static ItemStack setDioptre(ItemStack stack, float value) {
        stack.getOrCreateTag().putFloat("Dioptre", value);
        return stack;
    }

    public static float getDioptre(ItemStack stack) {
        if (stack.getItem() instanceof GlassesItem && stack.hasTag()) {
            return stack.getTag().getFloat("Dioptre");
        }
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tag, ITooltipFlag flag) {
        if (stack.getItem() instanceof GlassesItem && stack.hasTag() && getDioptre(stack) != 0) {
            tag.add(new TranslationTextComponent(this.getDescriptionId() + ".dioptre.desc", (getDioptre(stack) > 0 ? "+" : "") + getDioptre(stack)));
        }

    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.allowdedIn(group)) {
            for (float i = -10f; i <= 10f; i += 0.5f) {
                list.add(setDioptre(new ItemStack(this), i));
            }
        }
    }
}
