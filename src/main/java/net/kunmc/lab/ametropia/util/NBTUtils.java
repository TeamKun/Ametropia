package net.kunmc.lab.ametropia.util;

import net.kunmc.lab.ametropia.data.AmetropiaState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;

import java.util.*;
import java.util.function.Function;

public class NBTUtils {

    public static <T> CompoundNBT writeList(CompoundNBT tag, String name, List<T> list, Function<T, INBT> writer) {
        ListNBT listTag = new ListNBT();
        list.forEach(n -> listTag.add(writer.apply(n)));
        tag.put(name, listTag);
        return tag;
    }

    public static <T> void readList(CompoundNBT tag, String name, List<T> list, Function<INBT, T> reader, int num) {
        list.clear();
        ListNBT listTag = tag.getList(name, num);
        for (INBT lstag : listTag) {
            list.add(reader.apply(lstag));
        }
    }

    public static <T> void readList(CompoundNBT tag, String name, List<T> list, Function<INBT, T> reader) {
        readList(tag, name, list, reader, 10);
    }

    public static <T, M> CompoundNBT writeMap(CompoundNBT tag, String name, Map<T, M> map, Function<T, INBT> writerKey, Function<M, INBT> writerEntry) {

        Function<Map.Entry<T, M>, INBT> writer = n -> {
            CompoundNBT it = new CompoundNBT();
            it.put("K", writerKey.apply(n.getKey()));
            it.put("E", writerEntry.apply(n.getValue()));
            return it;
        };
        return writeList(tag, name, new ArrayList<>(map.entrySet()), writer);
    }

    public static <T, M> void readMap(CompoundNBT tag, String name, Map<T, M> map, Function<INBT, T> readerKey, Function<INBT, M> readerEntry, int num) {
        List<Map.Entry<T, M>> entries = new ArrayList<>();
        Function<INBT, Map.Entry<T, M>> reader = n -> {
            CompoundNBT it = (CompoundNBT) n;
            return new AbstractMap.SimpleEntry<>(readerKey.apply(it.get("K")), readerEntry.apply(it.get("E")));
        };
        readList(tag, name, entries, reader, num);
        map.clear();
        entries.forEach(n -> map.put(n.getKey(), n.getValue()));
    }


    public static CompoundNBT writeAmetropiaData(CompoundNBT tag, String name, Map<UUID, AmetropiaState> data) {
        return writeMap(tag, name, data, NBTUtil::createUUID, AmetropiaState::serializeNBT);
    }

    public static void readAmetropiaData(CompoundNBT tag, String name, Map<UUID, AmetropiaState> data) {
        readMap(tag, name, data, NBTUtil::loadUUID, n -> new AmetropiaState((CompoundNBT) n), 10);
    }

}
