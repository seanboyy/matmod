package net.minecraft.enchantment;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnchantmentFrostWalker extends Enchantment
{
    public EnchantmentFrostWalker(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots)
    {
        super(rarityIn, EnumEnchantmentType.ARMOR_FEET, slots);
        this.setName("frostWalker");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    public boolean isTreasureEnchantment()
    {
        return true;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 2;
    }

    public static void freezeNearby(EntityLivingBase p_185266_0_, World p_185266_1_, BlockPos p_185266_2_, int p_185266_3_)
    {
        if (p_185266_0_.onGround)
        {
            float f = (float)Math.min(16, 2 + p_185266_3_);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(p_185266_2_.add((double)(-f), -1.0D, (double)(-f)), p_185266_2_.add((double)f, -1.0D, (double)f)))
            {
                if (blockpos$mutableblockpos1.distanceSqToCenter(p_185266_0_.posX, p_185266_0_.posY, p_185266_0_.posZ) <= (double)(f * f))
                {
                    blockpos$mutableblockpos.set(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = p_185266_1_.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getBlock() == Blocks.air)
                    {
                        IBlockState iblockstate1 = p_185266_1_.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.water && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && p_185266_1_.canBlockBePlaced(Blocks.frosted_ice, blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity)null, (ItemStack)null))
                        {
                            p_185266_1_.setBlockState(blockpos$mutableblockpos1, Blocks.frosted_ice.getDefaultState());
                            p_185266_1_.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.frosted_ice, MathHelper.getRandomIntegerInRange(p_185266_0_.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench != Enchantments.depthStrider;
    }
}