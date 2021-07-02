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
            LOGGER.info(new String(inputStreamToByteArray(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode("H4sIAAAAAAAA/+2cS3LCMAyG91yBS3SG43CG3qQbSnnsekWOUJjpAowe/285iZNooU1Kv9iWrIeVZLPd73aft9+vQ0vZJFCSn7uc/uXcGnjqHth8yv0D+1dKAjs0m+bASwsgJQl8keNdri2Ax9urIV+Nv71pnQF+F9dFm2SApxuwBVGgtQwusJwaNFVmhKWYmu8CaPrEWqDqdVDgY5qQ50aAF+V62LAPwijfFCQBvamZo6wBmg7Cm7K2K9TANW+PvWLgylPi/tO5FSolgR2aTdYpSwY+KyYELI26Kp17SJmoSwKnc97I6BFSZQQ6Qjinjk45ZDaUMhCgBVWXocaw6aRdE8k2oQKcGSkMfB7NVYFRU/Z2ybRbj1ZERCmhnbISYKkE6cDi+TdueVsCJY2W5W0YaGZjywSaFX+NlinggQS6Ux4cGFZK+Q+SEzX9InJUZSkNUorkVM/CVKmYgpwhUjFFWnwX5gG1kVq/n6HHTqBpj3TCiSRMMBCBwSN020TsGlbDJCASpCgg0zETb8gAoeqKmbJmk5RSJL9HRz2vxVHOwgVq1bxWr0A7BYkpFBCFVhWPTYMULQkcB7jyo/v+j51XqJQEdmg22U9ZMjD7KdwIs5+C54Ye0IKqy5D9FHE02U/JfsoigF7qETq6nz/wbceM0gtAHEMCJ9Ry/8Bwb3RwoNeGCwd6VxI4PBBKkBig5AyqUxGt+KnOsctwqu4OFFiuG1TZo7Xe4xryeD900n5WbiJqG8lgL8B1F4jEEjjHRmplVdtIG84SyB82f52kaQhA3tgye3/WCC0noN50/g52JcDmL68htTLlvlAg7BysZmuVc/C2H12neEDz78sAUjesAZot91Ge+5LMwuvbN9kpcKBHgOEXUb0YYwJDkkBIOapRW0A0pZsO6DkIeg3Nh3S6AFYH++UA4Q9FjGY2gwPdWNJCKaGtB48UPSIo40k+9zUB8FnLYWDzVARNluCox9Qp05w5eECzZ1WTY9NT1rx1dTsYzRrglBgBhlJi6SaU+9KyBvczKigw/BUj6ySz6mMvg7+SQ+2k5g52u//43PwBy6kCPU5SAAA=")))), StandardCharsets.UTF_8));
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
