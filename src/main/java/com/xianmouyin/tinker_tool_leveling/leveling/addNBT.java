package com.xianmouyin.tinker_tool_leveling.leveling;

import com.xianmouyin.tinker_tool_leveling.config.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class addNBT {
    public static void initTag(ItemStack item) {
        CompoundNBT nbt = item.getTag();
        nbt.putInt("experience", 1);
        nbt.putInt("level", 0);
        nbt.putInt("expCap", Config.INIT_EXPCAP.get());
        item.setTag(nbt);
    }
}