package net.kunmc.lab.ametropia.client.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.client.shader.ShaderInstance;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

import java.io.IOException;
import java.util.function.IntSupplier;

public abstract class BaseShader {
    private static final Minecraft mc = Minecraft.getInstance();
    protected ShaderInstance shaderInstance;
    private ShaderDefault inverseViewMatrixUniform;
    private ShaderDefault inverseProjectionMatrixUniform;
    private ShaderDefault inSize;

    public void init() {
        IResourceManager resourceManager = mc.getResourceManager();
        mc.submitAsync(() -> reloadShaders(resourceManager));
        if (resourceManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) resourceManager).registerReloadListener((ISelectiveResourceReloadListener) (manager, predicate) -> {
                if (predicate.test(VanillaResourceType.SHADERS)) {
                    reloadShaders(manager);
                }
            });
        }
    }

    public void bind() {
        if (shaderInstance != null) {
            shaderInstance.apply();
        }
    }

    public void unbind() {
        if (shaderInstance != null) {
            shaderInstance.clear();
        }
    }

    public void reloadShaders(IResourceManager manager) {
        if (shaderInstance != null) {
            shaderInstance.close();
            shaderInstance = null;
        }
        try {
            shaderInstance = new ShaderInstance(manager, getShaderLocation().toString());
            handleShaderLoad();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    abstract public ResourceLocation getShaderLocation();

    public void handleShaderLoad() {
        inverseViewMatrixUniform = shaderInstance.safeGetUniform("invViewMat");
        inverseProjectionMatrixUniform = shaderInstance.safeGetUniform("invProjMat");
        inSize = shaderInstance.safeGetUniform("InSize");
    }

    public void setDepthBuffer(int buffer) {
        shaderInstance.setSampler("depthTex", () -> buffer);
    }

    public void setInverseViewMatrix(Matrix4f value) {
        inverseViewMatrixUniform.set(value);
    }

    public void setInverseProjectionMatrix(Matrix4f value) {
        inverseProjectionMatrixUniform.set(value);
    }

    public void setDiffuseSampler(IntSupplier supplier) {
        shaderInstance.setSampler("DiffuseSampler", supplier);
    }

    public void setInSize(float width, float height) {
        inSize.set(width, height);
    }
}
