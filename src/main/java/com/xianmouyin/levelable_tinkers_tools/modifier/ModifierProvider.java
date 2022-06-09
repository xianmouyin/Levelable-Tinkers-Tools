package com.xianmouyin.levelable_tinkers_tools.modifier;

import com.xianmouyin.levelable_tinkers_tools.levelable_tinkers_tools;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class ModifierProvider extends AbstractModifierProvider {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(levelable_tinkers_tools.MOD_ID);

    public static final StaticModifier<Modifier> levelable = MODIFIERS.register("levelable",LevelableModifier::new);

    public ModifierProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addModifiers() {}

    @Override
    public String getName() {
        return "Modifier";
    }
}
