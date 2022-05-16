package com.xianmouyin.tinker_tool_leveling.leveling;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
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
            CompoundNBT nbt = event.getItemStack().getTag();
            if (nbt.keySet().contains("experience") && nbt.keySet().contains("level") && nbt.keySet().contains("expCap")) {
                Integer exp = nbt.getInt("experience");
                Integer lvl = nbt.getInt("level");
                int expCap = nbt.getInt("expCap");

                List lore = event.getToolTip();

                lore.add(new TranslationTextComponent("tooltip.tinker_tool_leveling.exp").appendString(exp + "/" + expCap));
                lore.add(new TranslationTextComponent("tooltip.tinker_tool_leveling.lvl").appendString(lvl + ""));
            }
        }
    }
}