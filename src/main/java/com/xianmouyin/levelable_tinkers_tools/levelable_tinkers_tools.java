package com.xianmouyin.levelable_tinkers_tools;

import com.xianmouyin.levelable_tinkers_tools.config.Config;
import com.xianmouyin.levelable_tinkers_tools.modifier.ModifierRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(levelable_tinkers_tools.MOD_ID)
public class levelable_tinkers_tools {
    public static final String MOD_ID = "levelable_tinkers_tools";
    public levelable_tinkers_tools() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModifierRegister.MODIFIERS.register(bus);
    }
}