package com.omega4.testmod10.component;

import com.mojang.serialization.Codec;
import com.omega4.testmod10.Testmod10;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<BlockPos> COORDINATES = register("coordinates", builder -> builder.codec(BlockPos.CODEC));
    public static final ComponentType<Integer> CHARGE = register("charge", builder -> builder.codec(Codec.INT));
    public static final ComponentType<Boolean> INITIALIZED = register("initialized", builder -> builder.codec(Codec.BOOL)); //no longer needed
    //always need codec, do not do anything new

    //helperMethodToAdd
    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Testmod10.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes(){
        Testmod10.LOGGER.info("Registering Data Component Types for: " + Testmod10.MOD_ID);
    }
}
