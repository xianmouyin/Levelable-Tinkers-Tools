package com.xianmouyin.tinker_tool_leveling.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import net.minecraft.util.text.TranslationTextComponent;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ToolItem;

import static com.xianmouyin.tinker_tool_leveling.leveling.afterUse.levelUp;

public class levelUp implements Command<CommandSource> {
    public static levelUp instance = new levelUp();

    @Override
    public int run(CommandContext<CommandSource> context) {
        if (context.getSource().getEntity() instanceof PlayerEntity) {
            ItemStack stack = ((PlayerEntity) context.getSource().getEntity()).getHeldItemMainhand();
            if (stack.hasTag() && (stack.getItem() instanceof ToolItem || stack.getItem() instanceof ModifiableArmorItem)) {
                CompoundNBT nbt = stack.getTag();
                if (nbt.keySet().contains("level")){
                    int lvlNum = IntegerArgumentType.getInteger(context,"level_numbers");
                    while (lvlNum>0) {
                        nbt = levelUp(stack, (PlayerEntity) context.getSource().getEntity());
                        stack.setTag(nbt);
                        lvlNum--;
                    }
                } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.tinker_tool_leveling.error.2"));
            } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.tinker_tool_leveling.error.2"));
        } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.tinker_tool_leveling.error.1"));
        return 0;
    }
}