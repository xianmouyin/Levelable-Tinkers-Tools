package com.xianmouyin.tinker_tool_leveling.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import static com.xianmouyin.tinker_tool_leveling.leveling.afterUse.addExp;

public class expUp implements Command<CommandSourceStack> {
    public static expUp instance = new expUp();

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof Player){
            ItemStack stack = ((Player) context.getSource().getEntity()).getMainHandItem();
            if (stack.hasTag() && stack.getTag().contains("level")){
                if (stack.getItem() instanceof ModifiableItem || stack.getItem() instanceof ModifiableArmorItem) {
                    int exp = IntegerArgumentType.getInteger(context,"experience_numbers");
                    Player player = ((Player) context.getSource().getEntity());
                    addExp(stack,exp,player);
                    context.getSource().sendSuccess(new TranslatableComponent("msg.tinker_tool_leveling.success"), false);
                } else context.getSource().sendFailure(new TranslatableComponent("msg.tinker_tool_leveling.error.2"));
            } else context.getSource().sendFailure(new TranslatableComponent("msg.tinker_tool_leveling.error.2"));
        } else context.getSource().sendFailure(new TranslatableComponent("msg.tinker_tool_leveling.error.1"));
        return 0;
    }
}