package net.kunmc.lab.ametropia.client.handler;

import net.kunmc.lab.ametropia.client.SightManager;
import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        SightManager manager = SightManager.getInstance();
        manager.setLevel((float) (System.currentTimeMillis() % 10000) / 10000f);
        //   manager.setLevel(1f);
    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent.Text e) {
        if (mc.options.renderDebug) {

            boolean oneFlag = false;
            int dr = 0;
            int ct = 0;
            for (String s : e.getLeft()) {
                if (s.isEmpty()) {
                    if (!oneFlag) {
                        oneFlag = true;
                        continue;
                    } else {
                        dr = ct;
                        break;
                    }
                }
                ct++;
            }

            SightManager manager = SightManager.getInstance();
            e.getLeft().add(dr + 1, "Ametropia Info: [" + "Mode: " + manager.getType().getSerializedName() + (manager.getType() == AmetropiaType.NONE ? "" : ", Level: " + manager.getLevel()) + "]");
        }
    }
}
