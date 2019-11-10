package org.villainy.sweetconcrete.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.villainy.sweetconcrete.SweetConcrete;

final class CommonConfig {

    final ForgeConfigSpec.BooleanValue enableSlabs;
    final ForgeConfigSpec.BooleanValue enableStairs;
    final ForgeConfigSpec.BooleanValue enableWalls;
    final ForgeConfigSpec.BooleanValue enableButtons;
    final ForgeConfigSpec.BooleanValue enablePressurePlates;

    CommonConfig(final ForgeConfigSpec.Builder builder) {
        builder.push("general");
        enableSlabs = builder
                .comment("Enable concrete slabs")
                .translation(SweetConcrete.MODID + ".config.enableSlabs")
                .define("enableSlabs", true);
        enableStairs = builder
                .comment("Enable concrete stairs")
                .translation(SweetConcrete.MODID + ".config.enableStairs")
                .define("enableStairs", true);
        enableWalls = builder
                .comment("Enable concrete walls")
                .translation(SweetConcrete.MODID + ".config.enableWalls")
                .define("enableWalls", true);
        enableButtons = builder
                .comment("Enable concrete buttons")
                .translation(SweetConcrete.MODID + ".config.enableButtons")
                .define("enableButtons", true);
        enablePressurePlates = builder
                .comment("Enable concrete pressure plates")
                .translation(SweetConcrete.MODID + ".config.enablePressurePlates")
                .define("enablePressurePlates", true);
        builder.pop();
    }

}