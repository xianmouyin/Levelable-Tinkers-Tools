package com.xianmouyin.tinker_tool_leveling.leveling;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ToolItem;
import slimeknights.tconstruct.tools.item.small.SwordTool;

import java.util.Iterator;

import static com.xianmouyin.tinker_tool_leveling.leveling.afterUse.addExp;

@Mod.EventBusSubscriber()
public class onUse {
    static int num = 0;
    @SubscribeEvent
    public static void OnToolUse(BlockEvent.BreakEvent event) {
        if (num%2 == 0) {
            ItemStack tool = event.getPlayer().getHeldItemMainhand();
            if (tool.getItem() instanceof ToolItem) {
                BlockState block = event.getState();
                if(tool.canHarvestBlock(block)) {
                    addExp(tool,1, event.getPlayer());
                }
            }
        }
        num++;
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
            if (tool.getItem() instanceof SwordTool) {
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
                if (armor.getItem() instanceof ModifiableArmorItem) {
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
}