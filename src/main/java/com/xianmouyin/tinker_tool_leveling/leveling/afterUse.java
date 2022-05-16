package com.xianmouyin.tinker_tool_leveling.leveling;

import com.xianmouyin.tinker_tool_leveling.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;

import static com.xianmouyin.tinker_tool_leveling.leveling.addNBT.initTag;

public class afterUse {
    public static void addExp(ItemStack tool, Integer xp, Player player) {
        if (tool.hasTag()) {
            CompoundTag nbt = tool.getTag();
            if (nbt.getAllKeys().contains("experience") && nbt.getAllKeys().contains("level") && nbt.getAllKeys().contains("expCap")) {
                int exp = nbt.getInt("experience");
                int cap = nbt.getInt("expCap");
                exp += xp;
                while (exp >= cap) {
                    exp -= cap;
                    nbt = levelUp(tool, player);
                }
                nbt.putInt("experience", exp);
                tool.setTag(nbt);
            } else initTag(tool);
        }
    }

    public static CompoundTag levelUp(ItemStack stack, Player player) {
        CompoundTag nbt = stack.getTag();
        int cap = nbt.getInt("expCap");
        int lvl = nbt.getInt("level");
        lvl++;
        if (lvl <= 3) cap *= 2;
        else cap += Config.INIT_EXPCAP.get() * 2;
        CompoundTag slots = nbt.getCompound("tic_persistent_data");
        slots.putInt("upgrades", slots.getInt("upgrades") + 1);
        int abilityLvl = Config.ABILITY_LEVEL.get();
        int defenseLvl = Config.DEFENSE_LEVEL.get();
        if (abilityLvl != 0 && (lvl % abilityLvl) == 0) slots.putInt("abilities", slots.getInt("abilities") + 1);
        if (defenseLvl != 0 && stack.getItem() instanceof ModifiableArmorItem && (lvl % defenseLvl) == 0) slots.putInt("defense", slots.getInt("defense") + 1);
        nbt.put("tic_persistent_data", slots);
        nbt.putInt("level", lvl);
        nbt.putInt("expCap", cap);

        player.sendMessage(getLevelUpMsg(stack, lvl), player.getUUID());
        return nbt;
    }

    public static MutableComponent getLevelUpMsg(ItemStack stack, int lvl) {
        Component name = stack.getItem().getName(stack);
        MutableComponent text = new TranslatableComponent("msg.tinker_tool_leveling.leverUp.1").append(name).append(new TranslatableComponent("msg.tinker_tool_leveling.leverUp.2").append(lvl + "").append(new TranslatableComponent("msg.tinker_tool_leveling.leverUp.3")));
        return text;
    }
}