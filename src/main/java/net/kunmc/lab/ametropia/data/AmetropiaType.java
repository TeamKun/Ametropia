package net.kunmc.lab.ametropia.data;

import net.minecraft.util.IStringSerializable;

public enum AmetropiaType implements IStringSerializable {
    NONE("None"),
    HYPEROPIA("Hyperopia"),
    MYOPIA("Myopia");
    private final String name;

    AmetropiaType(String name) {
        this.name = name;
    }

    public static AmetropiaType getTypeByName(String name) {
        for (AmetropiaType sc : values()) {
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
