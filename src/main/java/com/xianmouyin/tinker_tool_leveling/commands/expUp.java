package com.xianmouyin.tinker_tool_leveling.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ToolItem;

import static com.xianmouyin.tinker_tool_leveling.leveling.afterUse.addExp;

public class expUp implements Command<CommandSource> {
    public static expUp instance = new expUp();

    @Override
    public int run(CommandContext<CommandSource> context) {
        if (context.getSource().getEntity() instanceof PlayerEntity){
            ItemStack stack = ((PlayerEntity) context.getSource().getEntity()).getHeldItemMainhand();
            if (stack.hasTag() && stack.getTag().contains("level")){
                if (stack.getItem() instanceof ToolItem || stack.getItem() instanceof ModifiableArmorItem) {
                    int exp = IntegerArgumentType.getInteger(context,"experience_numbers");
                    PlayerEntity player = ((PlayerEntity) context.getSource().getEntity());
                    addExp(stack,exp,player);
                    context.getSource().sendFeedback(new TranslationTextComponent("msg.tinker_tool_leveling.success"), false);
                } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.tinker_tool_leveling.error.2"));
            } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.tinker_tool_leveling.error.2"));
        } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.tinker_tool_leveling.error.1"));
        return 0;
    }
}