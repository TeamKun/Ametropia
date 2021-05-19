package net.kunmc.lab.ametropia.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IStringSerializable;

public class SightChangeMessage {
    public Type type;
    public float value;

    public SightChangeMessage(Type type, float value) {
        this.type = type;
        this.value = value;
    }

    public static SightChangeMessage decodeMessege(PacketBuffer buffer) {
        return new SightChangeMessage(Type.getTypeByName(buffer.readUtf(32767)), buffer.readFloat());
    }

    public static void encodeMessege(SightChangeMessage messegeIn, PacketBuffer buffer) {
        buffer.writeUtf(messegeIn.type.getSerializedName());
        buffer.writeFloat(messegeIn.value);
    }

    public static enum Type implements IStringSerializable {
        NONE("none"),
        FOCUS_UNIFORM("focus_uniform"),
        RANGE_UNIFORM("range_uniform"),
        DIFFERENCE_UNIFORM("difference_uniform"),
        IGNORE_DIST("ignore_dist");
        private final String name;

        Type(String name) {
            this.name = name;
        }

        public static Type getTypeByName(String name) {
            for (Type sc : values()) {
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
