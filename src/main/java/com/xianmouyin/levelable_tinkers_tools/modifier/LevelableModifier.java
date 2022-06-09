package com.xianmouyin.levelable_tinkers_tools.modifier;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.hooks.IElytraFlightModifier;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;

import static com.xianmouyin.levelable_tinkers_tools.leveling.afterUse.addExp;

public class LevelableModifier extends Modifier implements IElytraFlightModifier {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, int level, ToolHarvestContext context) {
        if (context.isEffective()) {
            Player player = context.getPlayer();
            addExp(player.getMainHandItem(),1,player);
        }
    }

    @Override
    @Nullable
    public <T> T getModule(Class<T> type) {
        return tryModuleMatch(type,IElytraFlightModifier.class,this);
    }

    @Override
    public boolean elytraFlightTick(IToolStackView tool, int level, LivingEntity entity, int flightTicks) {
        if (flightTicks % 10 == 0 && entity instanceof Player player) {
            addExp(player.getItemBySlot(EquipmentSlot.CHEST), 1, player);
        }
        return true;
    }
}
