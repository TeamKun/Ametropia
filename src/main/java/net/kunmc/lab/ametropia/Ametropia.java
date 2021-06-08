package net.kunmc.lab.ametropia;

import net.kunmc.lab.ametropia.client.handler.ClientHandler;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Ametropia.MODID)
public class Ametropia {
    private static final Logger LOGGER = LogManager.getLogger(Ametropia.class);
    public static final String MODID = "ametropia";

    public Ametropia() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        PacketHandler.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ServerHandler.class);
        LOGGER.info("\n" +
                "    \u001b[00;43m ######  ### ###   ######   #####   ##   ##   #####    ###### \u001b[00m\n" +
                "    \u001b[00;43m   ##     ## ##      ##    ##   ##  ##   ##  ##   ##     ##   \u001b[00m\n" +
                "    \u001b[00;43m   ##     ####       ##    ##       ##   ##  ##          ##   \u001b[00m\n" +
                "    \u001b[00;43m   ##     ###        ##     #####   ##   ##  ## ####     ##   \u001b[00m\n" +
                "    \u001b[00;43m   ##     ####       ##         ##  ##   ##  ##   ##     ##   \u001b[00m\n" +
                "    \u001b[00;43m   ##     ## ##      ##    ##   ##  ##   ##  ##   ##     ##   \u001b[00m\n" +
                "\u001b[00;32mThe \u001b[00;43m ######  ### ###   ######   #####    #####    #####    ###### \u001b[00;31m Shader System...\u001b[00m" +
                "");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(RenderHandler.class);
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);
        AmetropiaShader.getInstance().init();
        HyperopiaShader.getInstance().init();
        MyopiaShader.getInstance().init();
    }
}
