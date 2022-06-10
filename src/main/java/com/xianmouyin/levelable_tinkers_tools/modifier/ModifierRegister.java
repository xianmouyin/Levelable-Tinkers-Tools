package com.xianmouyin.levelable_tinkers_tools.modifier;

import com.xianmouyin.levelable_tinkers_tools.levelable_tinkers_tools;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class ModifierRegister {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(levelable_tinkers_tools.MOD_ID);

    public static final StaticModifier<Modifier> levelable = MODIFIERS.register("levelable",LevelableModifier::new);
}
