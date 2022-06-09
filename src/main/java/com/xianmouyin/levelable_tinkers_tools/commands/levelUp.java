package com.xianmouyin.levelable_tinkers_tools.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import static com.xianmouyin.levelable_tinkers_tools.leveling.afterUse.levelUp;

public class levelUp implements Command<CommandSourceStack> {
    public static levelUp instance = new levelUp();

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof Player) {
            ItemStack stack = ((Player) context.getSource().getEntity()).getMainHandItem();
            if (stack.hasTag() && (stack.getItem() instanceof ModifiableItem || stack.getItem() instanceof ModifiableArmorItem)) {
                CompoundTag nbt = stack.getTag();
                if (nbt.getAllKeys().contains("level")){
                    int lvlNum = IntegerArgumentType.getInteger(context,"level_number");
                    while (lvlNum>0) {
                        nbt = levelUp(stack, (Player) context.getSource().getEntity());
                        stack.setTag(nbt);
                        lvlNum--;
                    }
                } else context.getSource().sendFailure(new TranslatableComponent("msg.levelable_tinkers_tools.error.2"));
            } else context.getSource().sendFailure(new TranslatableComponent("msg.levelable_tinkers_tools.error.2"));
        } else context.getSource().sendFailure(new TranslatableComponent("msg.levelable_tinkers_tools.error.1"));
        return 0;
    }
}