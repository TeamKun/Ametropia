package net.kunmc.lab.ametropia.client.renderer;

import net.kunmc.lab.ametropia.client.shader.AMShaders;

import java.util.function.Consumer;

public class AMShaderRenderers {
    private static final SightShaderBaseRenderer HYPEROPIA_X_INSTANCE = new SightShaderBaseRenderer(AMShaders.getHyperopiaXInstance(), false);
    private static final SightShaderBaseRenderer HYPEROPIA_Y_INSTANCE = new SightShaderBaseRenderer(AMShaders.getHyperopiaYInstance(), false);
    private static final SightShaderBaseRenderer MYOPIA_X_INSTANCE = new SightShaderBaseRenderer(AMShaders.getMyopiaXInstance(), true);
    private static final SightShaderBaseRenderer MYOPIA_Y_INSTANCE = new SightShaderBaseRenderer(AMShaders.getMyopiaYInstance(), true);

    public static SightShaderBaseRenderer getHyperopiaXInstance() {
        return HYPEROPIA_X_INSTANCE;
    }

    public static SightShaderBaseRenderer getHyperopiaYInstance() {
        return HYPEROPIA_Y_INSTANCE;
    }

    public static SightShaderBaseRenderer getMyopiaXInstance() {
        return MYOPIA_X_INSTANCE;
    }

    public static SightShaderBaseRenderer getMyopiaYInstance() {
        return MYOPIA_Y_INSTANCE;
    }

    public static void allDoing(Consumer<SightShaderBaseRenderer> doing) {
        doing.accept(HYPEROPIA_X_INSTANCE);
        doing.accept(HYPEROPIA_Y_INSTANCE);
        doing.accept(MYOPIA_X_INSTANCE);
        doing.accept(MYOPIA_Y_INSTANCE);
    }
}
