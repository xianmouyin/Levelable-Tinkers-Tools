package com.xianmouyin.levelable_tinkers_tools.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.hooks.IElytraFlightModifier;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import javax.annotation.Nullable;

import static com.xianmouyin.levelable_tinkers_tools.leveling.afterUse.addExp;

public class LevelableModifier extends Modifier implements IElytraFlightModifier {
    public LevelableModifier() {
        super(0xffffff);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }

    @Override
    public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context) {
        if (context.isEffective()) {
            PlayerEntity player = context.getPlayer();
            addExp(player.getHeldItemMainhand(),1,player);
        }
    }

    @Nullable
    @Override
    public <T> T getModule(Class<T> type) {
        return tryModuleMatch(type,IElytraFlightModifier.class,this);
    }

    @Override
    public boolean elytraFlightTick(IModifierToolStack var1, int var2, LivingEntity var3, int var4) {
        if (var4 % 10 == 0 && var3 instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) var3;
            addExp(player.getItemStackFromSlot(EquipmentSlotType.CHEST),1,player);
        }
        return true;
    }
}
