package net.kunmc.lab.ametropia.client.renderer.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.client.util.RenderUtil;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GlassesRenderer extends ItemStackTileEntityRenderer {
    private static final ResourceLocation GLASSES_ITEM = new ResourceLocation(Ametropia.MODID, "item/glasses_item");
    private static final ResourceLocation GLASSES_ARMOR = new ResourceLocation(Ametropia.MODID, "item/glasses_armor");

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType type, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        IBakedModel model = RenderUtil.getBakedModel(type == ItemCameraTransforms.TransformType.HEAD ? GLASSES_ARMOR : GLASSES_ITEM);
        IVertexBuilder ivb = buffer.getBuffer(Atlases.cutoutBlockSheet());
        RenderUtil.renderBakedModel(matrixStack, ivb, null, model, combinedLight, combinedOverlay);
    }
}
