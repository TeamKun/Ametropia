package net.kunmc.lab.ametropia.client.shader;

import java.util.function.Consumer;

public class AMShaders {
    private static final SightBaseShader HYPEROPIA_X_INSTANCE = new SightBaseShader("hyperopia_x");
    private static final SightBaseShader HYPEROPIA_Y_INSTANCE = new SightBaseShader("hyperopia_y");
    private static final SightBaseShader MYOPIA_X_INSTANCE = new SightBaseShader("myopia_x");
    private static final SightBaseShader MYOPIA_Y_INSTANCE = new SightBaseShader("myopia_y");

    public static SightBaseShader getHyperopiaXInstance() {
        return HYPEROPIA_X_INSTANCE;
    }

    public static SightBaseShader getHyperopiaYInstance() {
        return HYPEROPIA_Y_INSTANCE;
    }

    public static SightBaseShader getMyopiaXInstance() {
        return MYOPIA_X_INSTANCE;
    }

    public static SightBaseShader getMyopiaYInstance() {
        return MYOPIA_Y_INSTANCE;
    }


    public static void allDoing(Consumer<SightBaseShader> doing) {
        doing.accept(HYPEROPIA_X_INSTANCE);
        doing.accept(HYPEROPIA_Y_INSTANCE);
        doing.accept(MYOPIA_X_INSTANCE);
        doing.accept(MYOPIA_Y_INSTANCE);
    }
}
