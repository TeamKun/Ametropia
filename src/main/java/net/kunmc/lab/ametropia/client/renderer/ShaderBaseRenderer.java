package net.kunmc.lab.ametropia.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.kunmc.lab.ametropia.client.shader.BaseShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.FramebufferConstants;
import net.minecraft.client.util.JSONBlendingMode;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public abstract class ShaderBaseRenderer<T extends BaseShader> {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final JSONBlendingMode RESET_BLEND_STATE = new JSONBlendingMode();
    public int depthCopyFbo;
    private int depthCopyColorBuffer;
    private int depthCopyDepthBuffer;

  /*  public void onRender(RenderWorldLastEvent e) {
        if (depthCopyFbo == 0) {
            createDepthCopyFramebuffer();
        }
        render(e.getMatrixStack().last().pose(), e.getProjectionMatrix());
    }*/

    public void doRender(final MatrixStack matrixStack, final Matrix4f projectionMatrix, float parTick) {

        if (depthCopyFbo == 0) {
            createDepthCopyFramebuffer();
        }

        render(matrixStack.last().pose(), projectionMatrix, parTick);
    }

    public void render(Matrix4f viewMatrix, Matrix4f projectionMatrix, float parTick) {
        Framebuffer framebuffer = mc.getMainRenderTarget();
        updateDepthTexture(framebuffer);

        Matrix4f invertedViewMatrix = new Matrix4f(viewMatrix);
        invertedViewMatrix.invert();
        getShader().setInverseViewMatrix(invertedViewMatrix);

        Matrix4f invertedProjectionMatrix = new Matrix4f(projectionMatrix);
        invertedProjectionMatrix.invert();

        getShader().setInverseProjectionMatrix(invertedProjectionMatrix);
        getShader().setDiffuseSampler(framebuffer::getColorTextureId);
        getShader().setInSize(framebuffer.width, framebuffer.height);

        setter(framebuffer, parTick);

        RESET_BLEND_STATE.apply();

        getShader().bind();

        blit(framebuffer);

        getShader().unbind();

    }

    public void setter(Framebuffer framebuffer, float parTick) {

    }

    public void deleteDepthCopyFramebuffer() {
        getShader().setDepthBuffer(0);

        GlStateManager._glDeleteFramebuffers(depthCopyFbo);
        depthCopyFbo = 0;

        TextureUtil.releaseTextureId(depthCopyColorBuffer);
        depthCopyColorBuffer = 0;

        TextureUtil.releaseTextureId(depthCopyDepthBuffer);
        depthCopyDepthBuffer = 0;
    }

    private void blit(final Framebuffer framebuffer) {
        final int width = framebuffer.width;
        final int height = framebuffer.height;

        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();

        setupMatrices(width, height);

        framebuffer.bindWrite(false);

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.vertex(0, height, 0).uv(0, 0).endVertex();
        buffer.vertex(width, height, 0).uv(1, 0).endVertex();
        buffer.vertex(width, 0, 0).uv(1, 1).endVertex();
        buffer.vertex(0, 0, 0).uv(0, 1).endVertex();
        tessellator.end();

        restoreMatrices();

        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
    }

    private void restoreMatrices() {
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.popMatrix();
    }

    private void setupMatrices(final int width, final int height) {
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0, width, height, 0, 1000, 3000);
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.translated(0, 0, -2000);
        RenderSystem.viewport(0, 0, width, height);
    }

    private void createDepthCopyFramebuffer() {
        final Framebuffer framebuffer = Minecraft.getInstance().getMainRenderTarget();

        depthCopyFbo = GlStateManager.glGenFramebuffers();

        depthCopyColorBuffer = createTexture(framebuffer.width, framebuffer.height, GL11.GL_RGBA8, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE);

        depthCopyDepthBuffer = createTexture(framebuffer.width, framebuffer.height, GL30.GL_DEPTH_COMPONENT, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT);

        GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, depthCopyFbo);
        GlStateManager._glFramebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, depthCopyColorBuffer, 0);
        GlStateManager._glFramebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthCopyDepthBuffer, 0);
        GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);

        getShader().setDepthBuffer(depthCopyDepthBuffer);
    }

    private int createTexture(final int width, final int height, final int internalFormat, final int format, final int type) {
        final int texture = TextureUtil.generateTextureId();
        GlStateManager._bindTexture(texture);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL14.GL_DEPTH_TEXTURE_MODE, GL11.GL_LUMINANCE);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_MODE, GL14.GL_NONE);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_FUNC, GL11.GL_LEQUAL);
        GlStateManager._texImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, type, null);
        GlStateManager._bindTexture(0);
        return texture;
    }

    private void updateDepthTexture(final Framebuffer framebuffer) {
        GlStateManager._glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, framebuffer.frameBufferId);
        GlStateManager._glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, depthCopyFbo);
        GL30.glBlitFramebuffer(0, 0, framebuffer.width, framebuffer.height,
                0, 0, framebuffer.width, framebuffer.height,
                GL30.GL_DEPTH_BUFFER_BIT, GL30.GL_NEAREST);

    }

    abstract public T getShader();

    public void resized() {
        if (depthCopyFbo != 0) {
            deleteDepthCopyFramebuffer();
        }
    }
}
