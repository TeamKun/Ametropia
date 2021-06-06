package net.kunmc.lab.ametropia.data;

import net.kunmc.lab.ametropia.util.NBTUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AmetropiaManager implements INBTSerializable<CompoundNBT> {
    private static final AmetropiaManager INSTANCE = new AmetropiaManager();
    private static Map<UUID, AmetropiaState> playerStates = new HashMap<>();

    public static AmetropiaManager getInstance() {
        return INSTANCE;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        NBTUtils.writeAmetropiaData(tag, "Data", playerStates);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) {
        NBTUtils.readAmetropiaData(tag, "Data", playerStates);
    }

    public Path getPath() {
        return Paths.get("AmetropiaStateData.dat");
    }


    public void saveFile(File file) {
        CompoundNBT compoundnbt = new CompoundNBT();
        compoundnbt.put("data", this.serializeNBT());
        compoundnbt.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());

        try {
            CompressedStreamTools.writeCompressed(compoundnbt, file.toPath().resolve(getPath()).toFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void unload() {
        playerStates.clear();
    }

    public AmetropiaState getPlayerState(PlayerEntity player) {
        UUID id = player.getGameProfile().getId();
        if (!playerStates.containsKey(id))
            playerStates.put(id, new AmetropiaState(AmetropiaType.NONE, 0));

        return playerStates.get(id);
    }

    public void setPlayerStateType(PlayerEntity player, AmetropiaType type) {
        UUID id = player.getGameProfile().getId();

        if (!playerStates.containsKey(id))
            playerStates.put(id, new AmetropiaState(AmetropiaType.NONE, 0));

        playerStates.get(id).setType(type);
    }

    public void setPlayerStateLevel(PlayerEntity player, float level) {
        UUID id = player.getGameProfile().getId();

        if (!playerStates.containsKey(id))
            playerStates.put(id, new AmetropiaState(AmetropiaType.NONE, 0));

        playerStates.get(id).setLevel(level);
    }

    public void load(File file) {
        try {
            File saveFile = file.toPath().resolve(getPath()).toFile();
            if (saveFile.exists())
                deserializeNBT(CompressedStreamTools.readCompressed(saveFile).getCompound("data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
