package com.xianmouyin.levelable_tinkers_tools.nbt;

import com.xianmouyin.levelable_tinkers_tools.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class levelNBT {
    public static int getNBT(ItemStack stack,String key) {
        CompoundTag nbt = stack.getTag();
        if (nbt.contains(key)) {
            return nbt.getInt(key);
        }
        return 0;
    }

    public static void setNBT(ItemStack stack,String key,int num) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt(key,num);
        stack.setTag(nbt);
    }

    public static void add(ItemStack stack,String key,int num) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt(key,getNBT(stack,key) + num);
        stack.setTag(nbt);
    }

    public static void initNBT(ItemStack stack) {
        setNBT(stack,"level",0);
        setNBT(stack,"experience",0);
        setNBT(stack,"expCap", Config.INIT_EXPCAP.get());
    }

    public static boolean detectLeveling(ItemStack stack) {
        int xp = getNBT(stack,"experience");
        int expCap = getNBT(stack,"expCap");
        return xp >= expCap;
    }

    public static boolean hasNBT(ItemStack stack) {
        if (stack.hasTag()) {
            Set<String> keys = stack.getTag().getAllKeys();
            return keys.contains("level") && keys.contains("experience") && keys.contains("expCap");
        }
        return false;
    }
}
