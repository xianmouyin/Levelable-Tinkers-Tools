package com.xianmouyin.levelable_tinkers_tools.leveling;

import com.xianmouyin.levelable_tinkers_tools.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static com.xianmouyin.levelable_tinkers_tools.nbt.levelNBT.*;

public class afterUse {
    public static void addExp(ItemStack tool, Integer xp, Player player) {
        if (hasNBT(tool)) {
            add(tool,"experience",xp);
            while (detectLeveling(tool)) {
                int cap = getNBT(tool,"expCap");
                add(tool,"experience", -cap);
                levelUp(tool, player);
            }
        } else {
            initNBT(tool);
            addExp(tool,xp,player);
        }
    }

    public static CompoundTag levelUp(ItemStack stack, Player player) {
        CompoundTag nbt = stack.getTag();
        int cap;
        int lvl = getNBT(stack,"level");
        int initCap = Config.INIT_EXPCAP.get();
        lvl++;
        if (lvl <= 3) cap = initCap * (2 ^ lvl);
        else cap = initCap * (5 + lvl);
        ModDataNBT slots = ToolStack.from(stack).getPersistentData();
        slots.setSlots(SlotType.UPGRADE,slots.getSlots(SlotType.UPGRADE) + 1);
        int abilityLvl = Config.ABILITY_LEVEL.get();
        int defenseLvl = Config.DEFENSE_LEVEL.get();
        if (abilityLvl != 0 && (lvl % abilityLvl) == 0) slots.setSlots(SlotType.ABILITY,slots.getSlots(SlotType.ABILITY)+1);
        if (defenseLvl != 0 && stack.getItem() instanceof ModifiableArmorItem && (lvl % defenseLvl) == 0) slots.setSlots(SlotType.DEFENSE,slots.getSlots(SlotType.DEFENSE)+1);
        setNBT(stack,"level",lvl);
        setNBT(stack,"expCap",cap);

        player.sendMessage(getLevelUpMsg(stack, lvl), player.getUUID());
        ServerPlayer serverPlayer = player.level.getServer().getPlayerList().getPlayer(player.getUUID());
        serverPlayer.connection.send(new ClientboundCustomSoundPacket(new ResourceLocation("entity.player.levelup"), SoundSource.PLAYERS, new Vec3(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ()), 1.0F, 1.0F));

        return nbt;
    }

    public static MutableComponent getLevelUpMsg(ItemStack stack, int lvl) {
        Component name = stack.getItem().getName(stack);
        return new TranslatableComponent("msg.levelable_tinkers_tools.leverUp.1").append(name).append(new TranslatableComponent("msg.levelable_tinkers_tools.leverUp.2").append(lvl + "").append(new TranslatableComponent("msg.levelable_tinkers_tools.leverUp.3")));
    }
}