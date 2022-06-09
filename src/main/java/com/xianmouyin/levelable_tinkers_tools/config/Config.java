package com.xianmouyin.levelable_tinkers_tools.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> ABILITY_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> DEFENSE_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> INIT_EXPCAP;

    static {
        builder.comment("General settings").push("general");

        ABILITY_LEVEL = builder.comment("Add ability slot per how many levels (write 0 to disable)").defineInRange("ABILITY_LEVEL",3,0,Integer.MAX_VALUE);
        DEFENSE_LEVEL = builder.comment("Add defense slot per how many levels (write 0 to disable)").defineInRange("DEFENSE_LEVEL",3,0,Integer.MAX_VALUE);
        INIT_EXPCAP = builder.comment("How many experience will your tools need to level up to level 1").defineInRange("INIT_EXPCAP",500,1,Integer.MAX_VALUE);

        builder.pop();
        COMMON_CONFIG = builder.build();
    }
}