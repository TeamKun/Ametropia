package net.kunmc.lab.ametropia;

import net.kunmc.lab.ametropia.client.handler.RenderHandler;
import net.kunmc.lab.ametropia.client.shader.AmetropiaShader;
import net.kunmc.lab.ametropia.client.shader.HyperopiaShader;
import net.kunmc.lab.ametropia.client.shader.MyopiaShader;
import net.kunmc.lab.ametropia.handler.ServerHandler;
import net.kunmc.lab.ametropia.packet.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ametropia.MODID)
public class Ametropia {
    public static final String MODID = "ametropia";

    public Ametropia() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        PacketHandler.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ServerHandler.class);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(RenderHandler.class);
        AmetropiaShader.getInstance().init();
        HyperopiaShader.getInstance().init();
        MyopiaShader.getInstance().init();
    }
}
