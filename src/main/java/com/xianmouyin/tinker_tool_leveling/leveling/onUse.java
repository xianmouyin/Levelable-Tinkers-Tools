package com.xianmouyin.tinker_tool_leveling.leveling;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.item.ModifiableSwordItem;

import static com.xianmouyin.tinker_tool_leveling.leveling.afterUse.addExp;

@Mod.EventBusSubscriber()
public class onUse {
    static int num = 0;
    @SubscribeEvent
    public static void OnToolUse(BlockEvent.BreakEvent event) {
        if (num%2 == 0) {
            ItemStack tool = event.getPlayer().getMainHandItem();
            if (tool.getItem() instanceof ModifiableItem) {
                BlockState block = event.getState();
                if(((ModifiableItem) tool.getItem()).getToolDefinition().getData().getHarvestLogic().isEffective(ToolStack.from(tool),block)) {
                    addExp(tool,1, event.getPlayer());
                }
            }
        }
        num++;
    }

    @SubscribeEvent
    public static void onHarvest(TinkerToolEvent.ToolHarvestEvent event) {
        ItemStack tool = event.getStack();
        if (tool.getItem() instanceof ModifiableItem) {
            addExp(tool,1,event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onShear(TinkerToolEvent.ToolShearEvent event) {
        ItemStack tool = event.getStack();
        if (tool.getItem() instanceof ModifiableItem) {
            addExp(tool,1,event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onLight(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld() instanceof ServerLevel && BaseFireBlock.canBePlacedAt(event.getWorld(), event.getPos().relative(event.getFace()), event.getFace())) {
            ItemStack tool = event.getItemStack();
            if (tool.hasTag() && tool.getTag().getAllKeys().contains("tic_modifiers")) {
                ListTag modifiers = (ListTag) tool.getTag().get("tic_modifiers");
                if (modifiers != null) {
                    for (Tag index : modifiers) {
                        CompoundTag tag = (CompoundTag) index;
                        if (tag.getAllKeys().contains("name")) {
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
        if (event.getEntity().getLevel() instanceof ServerLevel && event.getSource().getEntity() instanceof Player player) {
            ItemStack tool = player.getMainHandItem();
            if (tool.getItem() instanceof ModifiableSwordItem) {
                Integer xp = (int) Math.ceil(event.getAmount() / 2);
                addExp(tool,xp, player);
            }
        }
    }

    @SubscribeEvent
    public static void OnArmorUse(LivingHurtEvent event) {
        if (event.getEntity().getLevel() instanceof ServerLevel && !event.getSource().isBypassArmor() && event.getEntity() instanceof Player player) {
            for (ItemStack armor : player.getArmorSlots()) {
                if (armor.getItem() instanceof ModifiableArmorItem) {
                    Integer xp = (int) Math.ceil(event.getAmount() / 2);
                    addExp(armor, xp, player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnToolInteract(BlockEvent.BlockToolModificationEvent event) {
        ItemStack tool = event.getHeldItemStack();
        if (tool.getItem() instanceof ModifiableItem) {
            addExp(tool,1,event.getPlayer());
        }
    }
}