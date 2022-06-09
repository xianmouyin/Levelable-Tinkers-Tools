package com.xianmouyin.levelable_tinkers_tools.leveling;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.xianmouyin.levelable_tinkers_tools.nbt.levelNBT.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber()
public class addLore{
    @SubscribeEvent
    public static void RenderLore(ItemTooltipEvent event) {
        if(event.getItemStack().hasTag()){
            ItemStack stack = event.getItemStack();
            if (hasNBT(stack)) {
                int exp = getNBT(stack,"experience");
                int lvl = getNBT(stack,"level");
                int expCap = getNBT(stack,"expCap");

                List<Component> lore = event.getToolTip();
                lore.add(new TranslatableComponent("tooltip.levelable_tinkers_tools.exp").append(exp + "/" + expCap));
                lore.add(new TranslatableComponent("tooltip.levelable_tinkers_tools.lvl").append(lvl + ""));
            }
        }
    }
}