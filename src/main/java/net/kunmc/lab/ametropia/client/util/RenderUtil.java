package net.kunmc.lab.ametropia.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.HashMap;
import java.util.Map;

public class RenderUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<ResourceLocation, IBakedModel> BAKED_MODELS = new HashMap<ResourceLocation, IBakedModel>();

    public static void renderBakedModel(MatrixStack matrixStack, IVertexBuilder vertexBuilder, BlockState state, IBakedModel bakedModel, int combinedLight, int combinedOverlay) {
        BlockRendererDispatcher brd = mc.getBlockRenderer();
        BlockModelRenderer bmr = brd.getModelRenderer();
        bmr.renderModel(matrixStack.last(), vertexBuilder, state, bakedModel, 1.0F, 1.0F, 1.0F, combinedLight, combinedOverlay);
    }

    public static IBakedModel getBakedModel(ResourceLocation location) {

        if (BAKED_MODELS.containsKey(location))
            return BAKED_MODELS.get(location);

        IBakedModel model = ModelLoader.instance().bake(location, ModelRotation.X0_Y0);
        BAKED_MODELS.put(location, model);
        return model;
    }

    public static int getRenderDistance() {
        return Minecraft.getInstance().options.renderDistance * 16;
    }

}
