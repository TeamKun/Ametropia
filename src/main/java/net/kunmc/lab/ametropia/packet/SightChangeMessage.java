package net.kunmc.lab.ametropia.packet;

import net.kunmc.lab.ametropia.data.AmetropiaType;
import net.minecraft.network.PacketBuffer;

public class SightChangeMessage {
    public AmetropiaType type;
    public float level;

    public SightChangeMessage(AmetropiaType type, float level) {
        this.type = type;
        this.level = level;
    }

    public static SightChangeMessage decodeMessege(PacketBuffer buffer) {
        return new SightChangeMessage(AmetropiaType.getTypeByName(buffer.readUtf(32767)), buffer.readFloat());
    }

    public static void encodeMessege(SightChangeMessage messegeIn, PacketBuffer buffer) {
        buffer.writeUtf(messegeIn.type.getSerializedName());
        buffer.writeFloat(messegeIn.level);
    }
}
