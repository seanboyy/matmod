package net.minecraft.block;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoundType
{
    public static final SoundType WOOD = new SoundType(1.0F, 1.0F, SoundEvents.block_wood_break, SoundEvents.block_wood_step, SoundEvents.block_wood_place, SoundEvents.block_wood_hit, SoundEvents.block_wood_fall);
    public static final SoundType GROUND = new SoundType(1.0F, 1.0F, SoundEvents.block_gravel_break, SoundEvents.block_gravel_step, SoundEvents.block_gravel_place, SoundEvents.block_gravel_hit, SoundEvents.block_gravel_fall);
    public static final SoundType PLANT = new SoundType(1.0F, 1.0F, SoundEvents.block_grass_break, SoundEvents.block_grass_step, SoundEvents.block_grass_place, SoundEvents.block_grass_hit, SoundEvents.block_grass_fall);
    public static final SoundType STONE = new SoundType(1.0F, 1.0F, SoundEvents.block_stone_break, SoundEvents.block_stone_step, SoundEvents.block_stone_place, SoundEvents.block_stone_hit, SoundEvents.block_stone_fall);
    public static final SoundType METAL = new SoundType(1.0F, 1.5F, SoundEvents.block_metal_break, SoundEvents.block_metal_step, SoundEvents.block_metal_place, SoundEvents.block_metal_hit, SoundEvents.block_metal_fall);
    public static final SoundType GLASS = new SoundType(1.0F, 1.0F, SoundEvents.block_glass_break, SoundEvents.block_glass_step, SoundEvents.block_glass_place, SoundEvents.block_glass_hit, SoundEvents.block_glass_fall);
    public static final SoundType CLOTH = new SoundType(1.0F, 1.0F, SoundEvents.block_cloth_break, SoundEvents.block_cloth_step, SoundEvents.block_cloth_place, SoundEvents.block_cloth_hit, SoundEvents.block_cloth_fall);
    public static final SoundType SAND = new SoundType(1.0F, 1.0F, SoundEvents.block_sand_break, SoundEvents.block_sand_step, SoundEvents.block_sand_place, SoundEvents.block_sand_hit, SoundEvents.block_sand_fall);
    public static final SoundType SNOW = new SoundType(1.0F, 1.0F, SoundEvents.block_snow_break, SoundEvents.block_snow_step, SoundEvents.block_snow_place, SoundEvents.block_snow_hit, SoundEvents.block_snow_fall);
    public static final SoundType LADDER = new SoundType(1.0F, 1.0F, SoundEvents.block_ladder_break, SoundEvents.block_ladder_step, SoundEvents.block_ladder_place, SoundEvents.block_ladder_hit, SoundEvents.block_ladder_fall);
    public static final SoundType ANVIL = new SoundType(0.3F, 1.0F, SoundEvents.block_anvil_break, SoundEvents.block_anvil_step, SoundEvents.block_anvil_place, SoundEvents.block_anvil_hit, SoundEvents.block_anvil_fall);
    public static final SoundType SLIME = new SoundType(1.0F, 1.0F, SoundEvents.block_slime_break, SoundEvents.block_slime_step, SoundEvents.block_slime_place, SoundEvents.block_slime_hit, SoundEvents.block_slime_fall);
    public final float volume;
    public final float pitch;
    /** The sound played when a block gets broken. */
    private final SoundEvent breakSound;
    /** The sound played when walking on a block. */
    private final SoundEvent stepSound;
    /** The sound played when a block gets placed. */
    private final SoundEvent placeSound;
    /** The sound played when a block gets hit (i.e. while mining). */
    private final SoundEvent hitSound;
    /** The sound played when a block gets fallen upon. */
    private final SoundEvent fallSound;

    public SoundType(float volumeIn, float pitchIn, SoundEvent breakSoundIn, SoundEvent stepSoundIn, SoundEvent placeSoundIn, SoundEvent hitSoundIn, SoundEvent fallSoundIn)
    {
        this.volume = volumeIn;
        this.pitch = pitchIn;
        this.breakSound = breakSoundIn;
        this.stepSound = stepSoundIn;
        this.placeSound = placeSoundIn;
        this.hitSound = hitSoundIn;
        this.fallSound = fallSoundIn;
    }

    public float getVolume()
    {
        return this.volume;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    @SideOnly(Side.CLIENT)
    public SoundEvent getBreakSound()
    {
        return this.breakSound;
    }

    public SoundEvent getStepSound()
    {
        return this.stepSound;
    }

    public SoundEvent getPlaceSound()
    {
        return this.placeSound;
    }

    @SideOnly(Side.CLIENT)
    public SoundEvent getHitSound()
    {
        return this.hitSound;
    }

    public SoundEvent getFallSound()
    {
        return this.fallSound;
    }
}