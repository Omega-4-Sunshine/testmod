package com.omega4.testmod10.sound;

import com.omega4.testmod10.Testmod10;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    //sound assets need to be .ogg + mono -- also Sound event needs to be connected to sound file via sounds.json
    public static SoundEvent CHISEL_USE = registerSoundEvent("chisel_use");
    public static SoundEvent HEAL_BEAM_CHARGE = registerSoundEvent("heal_beam_charging");
    public static SoundEvent HEAL_BEAM_OVERHEAT = registerSoundEvent("heal_beam_overheat");

    private static SoundEvent STEEL_BlOCK_BREAK = registerSoundEvent("steel_block_break");
    private static SoundEvent STEEL_BlOCK_STEP = registerSoundEvent("steel_block_step");
    private static SoundEvent STEEL_BlOCK_PLACE = registerSoundEvent("steel_block_place");
    private static SoundEvent STEEL_BlOCK_HIT = registerSoundEvent("steel_block_hit");
    private static SoundEvent STEEL_BlOCK_FALL = registerSoundEvent("steel_block_fall");

    public static BlockSoundGroup STEEL_BLOCK_SOUNDS = new BlockSoundGroup(1,1,STEEL_BlOCK_BREAK,STEEL_BlOCK_STEP,STEEL_BlOCK_PLACE,STEEL_BlOCK_HIT,STEEL_BlOCK_FALL); //used when registering item -> all sounds con to block

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = Identifier.of(Testmod10.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSound() {
        Testmod10.LOGGER.info("Registering Sounds for: " + Testmod10.MOD_ID);
    }
}
