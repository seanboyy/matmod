package net.minecraft.init;

import com.mojang.authlib.GameProfile;
import java.io.PrintStream;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap
{
    public static final PrintStream SYSOUT = System.out;
    /** Whether the blocks, items, etc have already been registered */
    private static boolean alreadyRegistered = false;
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Is Bootstrap registration already done?
     */
    public static boolean isRegistered()
    {
        /** Whether the blocks, items, etc have already been registered */
        return alreadyRegistered;
    }

    static void registerDispenserBehaviors()
    {
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense()
        {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
            {
                EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, position.getX(), position.getY(), position.getZ());
                entitytippedarrow.canBePickedUp = EntityArrow.PickupStatus.ALLOWED;
                return entitytippedarrow;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.tipped_arrow, new BehaviorProjectileDispense()
        {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
            {
                EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, position.getX(), position.getY(), position.getZ());
                entitytippedarrow.setPotionEffect(stack);
                entitytippedarrow.canBePickedUp = EntityArrow.PickupStatus.ALLOWED;
                return entitytippedarrow;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spectral_arrow, new BehaviorProjectileDispense()
        {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
            {
                EntityArrow entityarrow = new EntitySpectralArrow(worldIn, position.getX(), position.getY(), position.getZ());
                entityarrow.canBePickedUp = EntityArrow.PickupStatus.ALLOWED;
                return entityarrow;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense()
        {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
            {
                return new EntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense()
        {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
            {
                return new EntitySnowball(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense()
        {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
            {
                return new EntityExpBottle(worldIn, position.getX(), position.getY(), position.getZ());
            }
            protected float getProjectileInaccuracy()
            {
                return super.getProjectileInaccuracy() * 0.5F;
            }
            protected float getProjectileVelocity()
            {
                return super.getProjectileVelocity() * 1.25F;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.splash_potion, new IBehaviorDispenseItem()
        {
            /**
             * Dispenses the specified ItemStack from a dispenser.
             */
            public ItemStack dispense(IBlockSource source, final ItemStack stack)
            {
                return (new BehaviorProjectileDispense()
                {
                    /**
                     * Return the projectile entity spawned by this dispense behavior.
                     */
                    protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
                    {
                        return new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
                    }
                    protected float getProjectileInaccuracy()
                    {
                        return super.getProjectileInaccuracy() * 0.5F;
                    }
                    protected float getProjectileVelocity()
                    {
                        return super.getProjectileVelocity() * 1.25F;
                    }
                }).dispense(source, stack);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lingering_potion, new IBehaviorDispenseItem()
        {
            /**
             * Dispenses the specified ItemStack from a dispenser.
             */
            public ItemStack dispense(IBlockSource source, final ItemStack stack)
            {
                return (new BehaviorProjectileDispense()
                {
                    /**
                     * Return the projectile entity spawned by this dispense behavior.
                     */
                    protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stack)
                    {
                        return new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
                    }
                    protected float getProjectileInaccuracy()
                    {
                        return super.getProjectileInaccuracy() * 0.5F;
                    }
                    protected float getProjectileVelocity()
                    {
                        return super.getProjectileVelocity() * 1.25F;
                    }
                }).dispense(source, stack);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem()
        {
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
                double d0 = source.getX() + (double)enumfacing.getFrontOffsetX();
                double d1 = (double)((float)source.getBlockPos().getY() + 0.2F);
                double d2 = source.getZ() + (double)enumfacing.getFrontOffsetZ();
                Entity entity = ItemMonsterPlacer.spawnCreature(source.getWorld(), ItemMonsterPlacer.getEntityIdFromItem(stack), d0, d1, d2);

                if (entity instanceof EntityLivingBase && stack.hasDisplayName())
                {
                    entity.setCustomNameTag(stack.getDisplayName());
                }

                ItemMonsterPlacer.applyItemEntityDataToEntity(source.getWorld(), (EntityPlayer)null, stack, entity);
                stack.splitStack(1);
                return stack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem()
        {
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
                double d0 = source.getX() + (double)enumfacing.getFrontOffsetX();
                double d1 = (double)((float)source.getBlockPos().getY() + 0.2F);
                double d2 = source.getZ() + (double)enumfacing.getFrontOffsetZ();
                EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(source.getWorld(), d0, d1, d2, stack);
                source.getWorld().spawnEntityInWorld(entityfireworkrocket);
                stack.splitStack(1);
                return stack;
            }
            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source)
            {
                source.getWorld().playAuxSFX(1004, source.getBlockPos(), 0);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem()
        {
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
                IPosition iposition = BlockDispenser.getDispensePosition(source);
                double d0 = iposition.getX() + (double)((float)enumfacing.getFrontOffsetX() * 0.3F);
                double d1 = iposition.getY() + (double)((float)enumfacing.getFrontOffsetY() * 0.3F);
                double d2 = iposition.getZ() + (double)((float)enumfacing.getFrontOffsetZ() * 0.3F);
                World world = source.getWorld();
                Random random = world.rand;
                double d3 = random.nextGaussian() * 0.05D + (double)enumfacing.getFrontOffsetX();
                double d4 = random.nextGaussian() * 0.05D + (double)enumfacing.getFrontOffsetY();
                double d5 = random.nextGaussian() * 0.05D + (double)enumfacing.getFrontOffsetZ();
                world.spawnEntityInWorld(new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
                stack.splitStack(1);
                return stack;
            }
            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source)
            {
                source.getWorld().playAuxSFX(1018, source.getBlockPos(), 0);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.OAK));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spruce_boat, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.SPRUCE));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.birch_boat, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.BIRCH));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.jungle_boat, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.JUNGLE));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dark_oak_boat, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.DARK_OAK));
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.acacia_boat, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.ACACIA));
        IBehaviorDispenseItem ibehaviordispenseitem = new BehaviorDefaultDispenseItem()
        {
            private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                ItemBucket itembucket = (ItemBucket)stack.getItem();
                BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));

                if (itembucket.tryPlaceContainedLiquid((EntityPlayer)null, source.getWorld(), blockpos))
                {
                    stack.setItem(Items.bucket);
                    stack.stackSize = 1;
                    return stack;
                }
                else
                {
                    return this.dispenseBehavior.dispense(source, stack);
                }
            }
        };
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, ibehaviordispenseitem);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, ibehaviordispenseitem);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem()
        {
            private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                World world = source.getWorld();
                BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
                IBlockState iblockstate = world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();
                Material material = iblockstate.getMaterial();
                Item item;

                if (Material.water.equals(material) && block instanceof BlockLiquid && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                {
                    item = Items.water_bucket;
                }
                else
                {
                    if (!Material.lava.equals(material) || !(block instanceof BlockLiquid) || ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() != 0)
                    {
                        return super.dispenseStack(source, stack);
                    }

                    item = Items.lava_bucket;
                }

                world.setBlockToAir(blockpos);

                if (--stack.stackSize == 0)
                {
                    stack.setItem(item);
                    stack.stackSize = 1;
                }
                else if (((TileEntityDispenser)source.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0)
                {
                    this.dispenseBehavior.dispense(source, new ItemStack(item));
                }

                return stack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem()
        {
            private boolean field_150839_b = true;
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                World world = source.getWorld();
                BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));

                if (world.isAirBlock(blockpos))
                {
                    world.setBlockState(blockpos, Blocks.fire.getDefaultState());

                    if (stack.attemptDamageItem(1, world.rand))
                    {
                        stack.stackSize = 0;
                    }
                }
                else if (world.getBlockState(blockpos).getBlock() == Blocks.tnt)
                {
                    Blocks.tnt.onBlockDestroyedByPlayer(world, blockpos, Blocks.tnt.getDefaultState().withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)));
                    world.setBlockToAir(blockpos);
                }
                else
                {
                    this.field_150839_b = false;
                }

                return stack;
            }
            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source)
            {
                if (this.field_150839_b)
                {
                    source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
                }
                else
                {
                    source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem()
        {
            private boolean field_150838_b = true;
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(stack.getMetadata()))
                {
                    World world = source.getWorld();
                    BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));

                    if (ItemDye.applyBonemeal(stack, world, blockpos))
                    {
                        if (!world.isRemote)
                        {
                            world.playAuxSFX(2005, blockpos, 0);
                        }
                    }
                    else
                    {
                        this.field_150838_b = false;
                    }

                    return stack;
                }
                else
                {
                    return super.dispenseStack(source, stack);
                }
            }
            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source)
            {
                if (this.field_150838_b)
                {
                    source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
                }
                else
                {
                    source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem()
        {
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                World world = source.getWorld();
                BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, (EntityLivingBase)null);
                world.spawnEntityInWorld(entitytntprimed);
                world.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.entity_tnt_primed, SoundCategory.BLOCKS, 1.0F, 1.0F);
                --stack.stackSize;
                return stack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.skull, new BehaviorDefaultDispenseItem()
        {
            private boolean field_179240_b = true;
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                World world = source.getWorld();
                EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
                BlockPos blockpos = source.getBlockPos().offset(enumfacing);
                BlockSkull blockskull = Blocks.skull;

                if (world.isAirBlock(blockpos) && blockskull.canDispenserPlace(world, blockpos, stack))
                {
                    if (!world.isRemote)
                    {
                        world.setBlockState(blockpos, blockskull.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.UP), 3);
                        TileEntity tileentity = world.getTileEntity(blockpos);

                        if (tileentity instanceof TileEntitySkull)
                        {
                            if (stack.getMetadata() == 3)
                            {
                                GameProfile gameprofile = null;

                                if (stack.hasTagCompound())
                                {
                                    NBTTagCompound nbttagcompound = stack.getTagCompound();

                                    if (nbttagcompound.hasKey("SkullOwner", 10))
                                    {
                                        gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
                                    }
                                    else if (nbttagcompound.hasKey("SkullOwner", 8))
                                    {
                                        String s = nbttagcompound.getString("SkullOwner");

                                        if (!StringUtils.isNullOrEmpty(s))
                                        {
                                            gameprofile = new GameProfile((UUID)null, s);
                                        }
                                    }
                                }

                                ((TileEntitySkull)tileentity).setPlayerProfile(gameprofile);
                            }
                            else
                            {
                                ((TileEntitySkull)tileentity).setType(stack.getMetadata());
                            }

                            ((TileEntitySkull)tileentity).setSkullRotation(enumfacing.getOpposite().getHorizontalIndex() * 4);
                            Blocks.skull.checkWitherSpawn(world, blockpos, (TileEntitySkull)tileentity);
                        }

                        --stack.stackSize;
                    }
                }
                else if (ItemArmor.func_185082_a(source, stack) == null)
                {
                    this.field_179240_b = false;
                }

                return stack;
            }
            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source)
            {
                if (this.field_179240_b)
                {
                    source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
                }
                else
                {
                    source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.pumpkin), new BehaviorDefaultDispenseItem()
        {
            private boolean field_179241_b = true;
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                World world = source.getWorld();
                BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
                BlockPumpkin blockpumpkin = (BlockPumpkin)Blocks.pumpkin;

                if (world.isAirBlock(blockpos) && blockpumpkin.canDispenserPlace(world, blockpos))
                {
                    if (!world.isRemote)
                    {
                        world.setBlockState(blockpos, blockpumpkin.getDefaultState(), 3);
                    }

                    --stack.stackSize;
                }
                else
                {
                    ItemStack itemstack = ItemArmor.func_185082_a(source, stack);

                    if (itemstack == null)
                    {
                        this.field_179241_b = false;
                    }
                }

                return stack;
            }
            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source)
            {
                if (this.field_179241_b)
                {
                    source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
                }
                else
                {
                    source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
                }
            }
        });
    }

    /**
     * Registers blocks, items, stats, etc.
     */
    public static void register()
    {
        if (!alreadyRegistered)
        {
            alreadyRegistered = true;

            if (LOGGER.isDebugEnabled())
            {
                redirectOutputToLog();
            }

            SoundEvent.registerSounds();
            Block.registerBlocks();
            BlockFire.init();
            Potion.registerPotions();
            Enchantment.registerEnchantments();
            Item.registerItems();
            PotionType.registerPotionTypes();
            PotionHelper.init();
            StatList.init();
            BiomeGenBase.registerBiomes();
            registerDispenserBehaviors();
        }
    }

    /**
     * redirect standard streams to logger
     */
    private static void redirectOutputToLog()
    {
        System.setErr(new LoggingPrintStream("STDERR", System.err));
        System.setOut(new LoggingPrintStream("STDOUT", SYSOUT));
    }

    @SideOnly(Side.CLIENT)
    public static void printToSYSOUT(String message)
    {
        SYSOUT.println(message);
    }

    public static class BehaviorDispenseBoat extends BehaviorDefaultDispenseItem
        {
            private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();
            private final EntityBoat.Type boatType;

            public BehaviorDispenseBoat(EntityBoat.Type boatTypeIn)
            {
                this.boatType = boatTypeIn;
            }

            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
                World world = source.getWorld();
                double d0 = source.getX() + (double)((float)enumfacing.getFrontOffsetX() * 1.125F);
                double d1 = source.getY() + (double)((float)enumfacing.getFrontOffsetY() * 1.125F);
                double d2 = source.getZ() + (double)((float)enumfacing.getFrontOffsetZ() * 1.125F);
                BlockPos blockpos = source.getBlockPos().offset(enumfacing);
                Material material = world.getBlockState(blockpos).getMaterial();
                double d3;

                if (Material.water.equals(material))
                {
                    d3 = 1.0D;
                }
                else
                {
                    if (!Material.air.equals(material) || !Material.water.equals(world.getBlockState(blockpos.down()).getMaterial()))
                    {
                        return this.dispenseBehavior.dispense(source, stack);
                    }

                    d3 = 0.0D;
                }

                EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);
                entityboat.setBoatType(this.boatType);
                entityboat.rotationYaw = enumfacing.getOpposite().getHorizontalAngle();
                world.spawnEntityInWorld(entityboat);
                stack.splitStack(1);
                return stack;
            }

            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source)
            {
                source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
            }
        }
}