package net.kunmc.lab.ametropia.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class AmetropiaState implements INBTSerializable<CompoundNBT> {
    private AmetropiaType type = AmetropiaType.NONE;
    private float level;

    public AmetropiaState(AmetropiaType type, float level) {
        this.type = type;
        this.level = level;
    }

    public AmetropiaState(CompoundNBT tag) {
        deserializeNBT(tag);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("Type", type.getSerializedName());
        tag.putFloat("Level", level);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) {
        this.type = AmetropiaType.getTypeByName(tag.getString("Type"));
        this.level = tag.getFloat("Level");
    }

    public AmetropiaType getType() {
        return type;
    }

    public float getLevel() {
        return level;
    }

    public void setType(AmetropiaType type) {
        this.type = type;
    }

    public void setLevel(float level) {
        this.level = level;
    }
}
