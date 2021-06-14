package net.kunmc.lab.ametropia.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class AmetropiaState implements INBTSerializable<CompoundNBT> {
    private float level;
    private float range;

    public AmetropiaState(float level, float range) {
        this.level = level;
        this.range = range;
    }

    public AmetropiaState(CompoundNBT tag) {
        deserializeNBT(tag);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("Level", level);
        tag.putFloat("Range", range);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) {
        this.level = tag.getFloat("Level");
        this.range = tag.getFloat("Range");
    }


    public float getLevel() {
        return level;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setLevel(float level) {
        this.level = level;
    }
}
