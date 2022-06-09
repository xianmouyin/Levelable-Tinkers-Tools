package com.xianmouyin.levelable_tinkers_tools.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ToolItem;

import static com.xianmouyin.levelable_tinkers_tools.leveling.afterUse.addExp;

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
                    context.getSource().sendFeedback(new TranslationTextComponent("msg.levelable_tinkers_tools.success"), false);
                } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.levelable_tinkers_tools.error.2"));
            } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.levelable_tinkers_tools.error.2"));
        } else context.getSource().sendErrorMessage(new TranslationTextComponent("msg.levelable_tinkers_tools.error.1"));
        return 0;
    }
}