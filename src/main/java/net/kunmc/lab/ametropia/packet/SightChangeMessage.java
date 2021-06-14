package net.kunmc.lab.ametropia.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IStringSerializable;

public class SightChangeMessage {
    public ChangeType changeType;
    public float range;
    public float level;

    public SightChangeMessage(ChangeType changeType, float level, float range) {
        this.changeType = changeType;
        this.level = level;
        this.range = range;
    }

    public static SightChangeMessage decodeMessege(PacketBuffer buffer) {
        return new SightChangeMessage(ChangeType.getTypeByName(buffer.readUtf(32767)), buffer.readFloat(), buffer.readFloat());
    }

    public static void encodeMessege(SightChangeMessage messegeIn, PacketBuffer buffer) {
        buffer.writeUtf(messegeIn.changeType.getSerializedName());
        buffer.writeFloat(messegeIn.level);
        buffer.writeFloat(messegeIn.range);
    }

    public static enum ChangeType implements IStringSerializable {
        NONE("None"),
        ALL("All"),
        LEVEL("Level"),
        RANGE("Range");

        private final String name;

        ChangeType(String name) {
            this.name = name;
        }

        public static ChangeType getTypeByName(String name) {
            for (ChangeType sc : values()) {
                if (sc.getSerializedName().equals(name))
                    return sc;
            }
            return NONE;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
