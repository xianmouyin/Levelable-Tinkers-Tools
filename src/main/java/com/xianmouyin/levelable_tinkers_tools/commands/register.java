package com.xianmouyin.levelable_tinkers_tools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class register {
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSource> cmd_levelUp = dispatcher.register(
                Commands.literal("LevelUpTool").then(
                        Commands.argument("level_numbers", IntegerArgumentType.integer(1))
                                .requires((commandSource -> commandSource.hasPermissionLevel(4)))
                                .executes(context -> levelUp.instance.run(context))
                        )
        );
        LiteralCommandNode<CommandSource> cmd_expUp = dispatcher.register(
                Commands.literal("addExp").then(
                        Commands.argument("experience_numbers", IntegerArgumentType.integer(1))
                                .requires((commandSource -> commandSource.hasPermissionLevel(4)))
                                .executes(context -> expUp.instance.run(context))
                )
        );
    }
}