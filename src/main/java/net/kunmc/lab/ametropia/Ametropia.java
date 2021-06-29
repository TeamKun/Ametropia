package net.kunmc.lab.ametropia;

import net.kunmc.lab.ametropia.client.data.SightManager;
import net.kunmc.lab.ametropia.client.handler.ClientHandler;
import net.kunmc.lab.ametropia.client.handler.RenderHandler;
import net.kunmc.lab.ametropia.handler.ServerHandler;
import net.kunmc.lab.ametropia.packet.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

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

        try {
            LOGGER.info(new String(inputStreamToByteArray(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode("H4sIAAAAAAAAAONSAALpaAMDaxPjXAVlMFAA0WCsABdRQNAIAiICUwMyJZcLxTyoSjAFZSnA9EAJTAaMwm8ezDhk8zANRhYkYB6KWiz+hQQJue6DM8n1L7nhBzbL2Cg3JCOVpHjGxYDEs7WxYa5CcEZiSmqRQnBlcUlqrp6eHthCADAHD6dRAgAA")))), StandardCharsets.UTF_8));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    private static byte[] inputStreamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = stream.read(buffer);
            if (len < 0) {
                break;
            }
            bout.write(buffer, 0, len);
        }
        return bout.toByteArray();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(RenderHandler.class);
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);

        SightManager.getInstance().init();
    }
}
