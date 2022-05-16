package com.xianmouyin.tinker_tool_leveling;

import com.xianmouyin.tinker_tool_leveling.config.Config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Utils.MOD_ID)
public class tinker_tool_leveling {
    public tinker_tool_leveling() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
    }
}