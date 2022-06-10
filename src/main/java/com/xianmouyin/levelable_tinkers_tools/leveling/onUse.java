package com.xianmouyin.levelable_tinkers_tools.leveling;

import com.xianmouyin.levelable_tinkers_tools.modifier.ModifierProvider;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ToolItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.item.small.SwordTool;

import java.util.Iterator;
import java.util.List;

import static com.xianmouyin.levelable_tinkers_tools.leveling.afterUse.addExp;

@Mod.EventBusSubscriber()
public class onUse {
    @SubscribeEvent
    public static void OnToolUse(BlockEvent.BreakEvent event) {
        ItemStack tool = event.getPlayer().getHeldItemMainhand();
        if (tool.getItem() instanceof ToolItem && ToolStack.from(tool).getModifiers().getLevel(ModifierProvider.levelable.get()) <= 0) {
            ToolStack.from(tool).addModifier(ModifierProvider.levelable.get(),1);
        }
    }

    @SubscribeEvent
    public static void onHarvest(TinkerToolEvent.ToolHarvestEvent event) {
        ItemStack tool = event.getStack();
        if (tool.getItem() instanceof ToolItem) {
            addExp(tool,1,event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onShear(TinkerToolEvent.ToolShearEvent event) {
        ItemStack tool = event.getStack();
        if (tool.getItem() instanceof ToolItem) {
            addExp(tool,1,event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onLight(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld() instanceof ServerWorld && AbstractFireBlock.canLightBlock(event.getWorld(), event.getPos().offset(event.getFace()), event.getFace())) {
            ItemStack tool = event.getItemStack();
            if (tool.hasTag() && tool.getTag().keySet().contains("tic_modifiers")) {
                ListNBT modifiers = (ListNBT) tool.getTag().get("tic_modifiers");
                if (modifiers != null) {
                    for (INBT index : modifiers) {
                        CompoundNBT tag = (CompoundNBT) index;
                        if (tag.keySet().contains("name")) {
                            if (tag.getString("name").equals("tconstruct:fiery") || tag.getString("name").equals("tconstruct:firestarter")) {
                                addExp(tool, 1, event.getPlayer());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnWeaponUse(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            ItemStack tool = player.getHeldItemMainhand();
            if (tool.getItem() instanceof SwordTool && !ToolStack.from(tool).isBroken()) {
                int damage = (int) Math.ceil(event.getAmount()/2);
                addExp(tool,damage, player);
            }
        }
    }

    @SubscribeEvent
    public static void OnArmorUse(LivingHurtEvent event) {
        if (!event.getSource().isUnblockable() && event.getEntity() instanceof PlayerEntity) {
            Iterator<ItemStack> armors = event.getEntity().getEquipmentAndArmor().iterator();
            while (armors.hasNext()) {
                ItemStack armor = armors.next();
                if (armor.getItem() instanceof ModifiableArmorItem && !ToolStack.from(armor).isBroken()) {
                    int damage = (int) Math.ceil(event.getAmount()/2);
                    addExp(armor,damage, ((PlayerEntity) event.getEntity()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnToolInteract(BlockEvent.BlockToolInteractEvent event) {
        ItemStack tool = event.getHeldItemStack();
        if (tool.getItem() instanceof ToolItem) {
            addExp(tool,1,event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void OnGlide(TickEvent.PlayerTickEvent event) {
        ItemStack elytra = event.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (event.player.isElytraFlying() && elytra.getItem() instanceof ModifiableArmorItem && ToolStack.from(elytra).getModifiers().getLevel(ModifierProvider.levelable.get()) <= 0) {
            ToolStack.from(elytra).addModifier(ModifierProvider.levelable.get(),1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void RenderLore(ItemTooltipEvent event) {
        if(event.getItemStack().hasTag()){
            CompoundNBT nbt = event.getItemStack().getTag();
            if (nbt.keySet().contains("experience") && nbt.keySet().contains("level") && nbt.keySet().contains("expCap")) {
                if (ToolStack.from(event.getItemStack()).getModifiers().getLevel(ModifierProvider.levelable.get()) <= 0) {
                    ToolStack.from(event.getItemStack()).addModifier(ModifierProvider.levelable.get(),1);
                }
                Integer exp = nbt.getInt("experience");
                Integer lvl = nbt.getInt("level");
                int expCap = nbt.getInt("expCap");

                List lore = event.getToolTip();

                lore.add(new TranslationTextComponent("tooltip.levelable_tinkers_tools.exp").appendString(exp + "/" + expCap));
                lore.add(new TranslationTextComponent("tooltip.levelable_tinkers_tools.lvl").appendString(lvl + ""));
            }
        }
    }
}