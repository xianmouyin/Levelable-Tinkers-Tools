package com.xianmouyin.levelable_tinkers_tools.modifier;

import com.xianmouyin.levelable_tinkers_tools.Utils;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.tconstruct.library.modifiers.Modifier;

public class ModifierProvider {
    public static final DeferredRegister<Modifier> MODIFIERS = DeferredRegister.create(Modifier.class,Utils.MOD_ID);

    public static final RegistryObject<Modifier> levelable = MODIFIERS.register("levelable",LevelableModifier::new);
}
