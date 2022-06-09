package com.xianmouyin.levelable_tinkers_tools.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import static com.xianmouyin.levelable_tinkers_tools.leveling.afterUse.addExp;

public class expUp implements Command<CommandSourceStack> {
    public static expUp instance = new expUp();

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof Player){
            ItemStack stack = ((Player) context.getSource().getEntity()).getMainHandItem();
            if (stack.hasTag() && stack.getTag().contains("experience")){
                if (stack.getItem() instanceof ModifiableItem || stack.getItem() instanceof ModifiableArmorItem) {
                    int exp = IntegerArgumentType.getInteger(context,"experience_number");
                    Player player = ((Player) context.getSource().getEntity());
                    addExp(stack,exp,player);
                    context.getSource().sendSuccess(new TranslatableComponent("msg.levelable_tinkers_tools.success"), false);
                } else context.getSource().sendFailure(new TranslatableComponent("msg.levelable_tinkers_tools.error.2"));
            } else context.getSource().sendFailure(new TranslatableComponent("msg.levelable_tinkers_tools.error.2"));
        } else context.getSource().sendFailure(new TranslatableComponent("msg.levelable_tinkers_tools.error.1"));
        return 0;
    }
}