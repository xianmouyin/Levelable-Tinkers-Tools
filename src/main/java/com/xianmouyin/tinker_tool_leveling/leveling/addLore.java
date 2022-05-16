package com.xianmouyin.tinker_tool_leveling.leveling;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber()
public class addLore{
    @SubscribeEvent
    public static void RenderLore(ItemTooltipEvent event) {
        if(event.getItemStack().hasTag()){
            CompoundTag nbt = event.getItemStack().getTag();
            if (nbt.getAllKeys().contains("experience") && nbt.getAllKeys().contains("level") && nbt.getAllKeys().contains("expCap")) {
                Integer exp = nbt.getInt("experience");
                Integer lvl = nbt.getInt("level");
                int expCap = nbt.getInt("expCap");

                List lore = event.getToolTip();

                lore.add(new TranslatableComponent("tooltip.tinker_tool_leveling.exp").append(exp + "/" + expCap));
                lore.add(new TranslatableComponent("tooltip.tinker_tool_leveling.lvl").append(lvl + ""));
            }
        }
    }
}