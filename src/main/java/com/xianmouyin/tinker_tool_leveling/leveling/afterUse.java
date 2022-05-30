package com.xianmouyin.tinker_tool_leveling.leveling;

import com.xianmouyin.tinker_tool_leveling.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;

import static com.xianmouyin.tinker_tool_leveling.leveling.addNBT.initTag;

public class afterUse {
    public static void addExp(ItemStack tool, Integer xp, PlayerEntity player) {
        if (tool.hasTag()) {
            CompoundNBT nbt = tool.getTag();
            if (nbt.keySet().contains("experience") && nbt.keySet().contains("level") && nbt.keySet().contains("expCap")) {
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

    public static CompoundNBT levelUp(ItemStack stack, PlayerEntity player) {
        CompoundNBT nbt = stack.getTag();
        int cap = nbt.getInt("expCap");
        int lvl = nbt.getInt("level");
        lvl++;
        if (lvl <= 3) cap *= 2;
        else cap += Config.INIT_EXPCAP.get() * 2;
        CompoundNBT slots = nbt.getCompound("tic_persistent_data");
        slots.putInt("upgrades", slots.getInt("upgrades") + 1);
        int abilityLvl = Config.ABILITY_LEVEL.get();
        int defenseLvl = Config.DEFENSE_LEVEL.get();
        if (abilityLvl != 0 && (lvl % abilityLvl) == 0) slots.putInt("abilities", slots.getInt("abilities") + 1);
        if (defenseLvl != 0 && stack.getItem() instanceof ModifiableArmorItem && (lvl % defenseLvl) == 0) slots.putInt("defense", slots.getInt("defense") + 1);
        nbt.put("tic_persistent_data", slots);
        nbt.putInt("level", lvl);
        nbt.putInt("expCap", cap);

        player.sendMessage(getLevelUpMsg(stack, lvl), player.getUniqueID());
        ServerPlayerEntity serverPlayer = player.world.getServer().getPlayerList().getPlayerByUUID(player.getUniqueID());
        serverPlayer.connection.sendPacket(new SPlaySoundPacket(new ResourceLocation("entity.player.levelup"), SoundCategory.PLAYERS,new Vector3d(serverPlayer.getPosX(), serverPlayer.getPosY(), serverPlayer.getPosZ()), 1.0F, 1.0F));
        return nbt;
    }

    public static ITextComponent getLevelUpMsg(ItemStack stack, int lvl) {
        String name = stack.getDisplayName().getString();
        return new TranslationTextComponent("msg.tinker_tool_leveling.leverUp.1").appendString(name).appendSibling(new TranslationTextComponent("msg.tinker_tool_leveling.leverUp.2").appendString(lvl + "").appendSibling(new TranslationTextComponent("msg.tinker_tool_leveling.leverUp.3")));
    }
}