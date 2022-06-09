package com.xianmouyin.levelable_tinkers_tools.leveling;

import com.xianmouyin.levelable_tinkers_tools.modifier.ModifierProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.item.ModifiableSwordItem;

import static com.xianmouyin.levelable_tinkers_tools.leveling.afterUse.addExp;

@Mod.EventBusSubscriber()
public class eventHandler {
    @SubscribeEvent
    public static void OnToolUse(BlockEvent.BreakEvent event) {
        ItemStack tool = event.getPlayer().getMainHandItem();
        if (tool.getItem() instanceof ModifiableItem && ToolStack.from(tool).getModifierLevel(ModifierProvider.levelable.getId()) <= 0) {
            ToolStack.from(tool).addModifier(ModifierProvider.levelable.getId(),1);
        }
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
                ModifierNBT modifiers = ToolStack.from(tool).getModifiers();
                if (modifiers.getLevel(new ModifierId("tconstruct:fiery"))>0 || modifiers.getLevel(new ModifierId("tconstruct:firestarter"))>0) {
                    addExp(tool, 1, event.getPlayer());
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnWeaponUse(LivingHurtEvent event) {
        if (event.getEntity().getLevel() instanceof ServerLevel && event.getSource().getEntity() instanceof Player player) {
            ItemStack tool = player.getMainHandItem();
            if (tool.getItem() instanceof ModifiableSwordItem && !ToolStack.from(tool).isBroken()) {
                Integer xp = (int) Math.ceil(event.getAmount() / 2);
                addExp(tool,xp, player);
            }
        }
    }

    @SubscribeEvent
    public static void OnArmorUse(LivingHurtEvent event) {
        if (event.getEntity().getLevel() instanceof ServerLevel && !event.getSource().isBypassArmor() && event.getEntity() instanceof Player player) {
            for (ItemStack armor : player.getArmorSlots()) {
                if (armor.getItem() instanceof ModifiableArmorItem && !ToolStack.from(armor).isBroken()) {
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

    @SubscribeEvent
    public static void OnElytraGlide(TickEvent.PlayerTickEvent event) {
        ItemStack elytra = event.player.getItemBySlot(EquipmentSlot.CHEST);
        if (event.player.isFallFlying() && elytra.getItem() instanceof ModifiableArmorItem && ToolStack.from(elytra).getModifierLevel(ModifierProvider.levelable.getId()) <= 0) {
            ToolStack.from(elytra).addModifier(ModifierProvider.levelable.getId(), 1);
        }
    }
}