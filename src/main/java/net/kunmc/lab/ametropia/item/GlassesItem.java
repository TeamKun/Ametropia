package net.kunmc.lab.ametropia.item;

import net.kunmc.lab.ametropia.client.renderer.item.GlassesRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

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


}
