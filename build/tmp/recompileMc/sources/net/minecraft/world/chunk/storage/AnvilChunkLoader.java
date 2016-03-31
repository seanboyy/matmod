package net.minecraft.world.chunk.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
    private static final Logger logger = LogManager.getLogger();
    private Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove = new ConcurrentHashMap();
    private Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates = Collections.<ChunkCoordIntPair>newSetFromMap(new ConcurrentHashMap());
    /** Save directory for chunks using the Anvil format */
    public final File chunkSaveLocation;
    private final DataFixer dataFixer;
    private boolean savingExtraData = false;

    public AnvilChunkLoader(File p_i46673_1_, DataFixer p_i46673_2_)
    {
        this.chunkSaveLocation = p_i46673_1_;
        this.dataFixer = p_i46673_2_;
    }

    public boolean chunkExists(World world, int x, int z)
    {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);

        if (this.pendingAnvilChunksCoordinates.contains(chunkcoordintpair))
        {
            for(ChunkCoordIntPair pendingChunkCoord : this.chunksToRemove.keySet())
            {
                if (pendingChunkCoord.equals(chunkcoordintpair))
                {
                    return true;
                }
            }
        }

        return RegionFileCache.createOrLoadRegionFile(this.chunkSaveLocation, x, z).chunkExists(x & 31, z & 31);
    }

    /**
     * Loads the specified(XZ) chunk into the specified world.
     */
    public Chunk loadChunk(World worldIn, int x, int z) throws IOException
    {
        Object[] data = this.loadChunk__Async(worldIn, x, z);

        if (data != null)
        {
            Chunk chunk = (Chunk) data[0];
            NBTTagCompound nbttagcompound = (NBTTagCompound) data[1];
            this.loadEntities(worldIn, nbttagcompound.getCompoundTag("Level"), chunk);
            return chunk;
        }

        return null;
    }

    public Object[] loadChunk__Async(World worldIn, int x, int z) throws IOException
    {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
        NBTTagCompound nbttagcompound = (NBTTagCompound)this.chunksToRemove.get(chunkcoordintpair);

        if (nbttagcompound == null)
        {
            DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);

            if (datainputstream == null)
            {
                return null;
            }

            nbttagcompound = this.dataFixer.process(FixTypes.CHUNK, CompressedStreamTools.read(datainputstream));
        }

        return this.checkedReadChunkFromNBT__Async(worldIn, x, z, nbttagcompound);
    }

    /**
     * Wraps readChunkFromNBT. Checks the coordinates and several NBT tags.
     */
    protected Chunk checkedReadChunkFromNBT(World worldIn, int x, int z, NBTTagCompound p_75822_4_)
    {
        Object[] data = this.checkedReadChunkFromNBT__Async(worldIn, x, z, p_75822_4_);
        return data != null ? (Chunk)data[0] : null;
    }

    protected Object[] checkedReadChunkFromNBT__Async(World worldIn, int x, int z, NBTTagCompound p_75822_4_)
    {
        if (!p_75822_4_.hasKey("Level", 10))
        {
            logger.error("Chunk file at " + x + "," + z + " is missing level data, skipping");
            return null;
        }
        else
        {
            NBTTagCompound nbttagcompound = p_75822_4_.getCompoundTag("Level");

            if (!nbttagcompound.hasKey("Sections", 9))
            {
                logger.error("Chunk file at " + x + "," + z + " is missing block data, skipping");
                return null;
            }
            else
            {
                Chunk chunk = this.readChunkFromNBT(worldIn, nbttagcompound);

                if (!chunk.isAtLocation(x, z))
                {
                    logger.error("Chunk file at " + x + "," + z + " is in the wrong location; relocating. (Expected " + x + ", " + z + ", got " + chunk.xPosition + ", " + chunk.zPosition + ")");
                    nbttagcompound.setInteger("xPos", x);
                    nbttagcompound.setInteger("zPos", z);

                    // Have to move tile entities since we don't load them at this stage
                    NBTTagList _tileEntities = nbttagcompound.getTagList("TileEntities", 10);

                    if (_tileEntities != null)
                    {
                        for (int te = 0; te < _tileEntities.tagCount(); te++)
                        {
                            NBTTagCompound _nbt = (NBTTagCompound) _tileEntities.getCompoundTagAt(te);
                            _nbt.setInteger("x", x * 16 + (_nbt.getInteger("x") - chunk.xPosition * 16));
                            _nbt.setInteger("z", z * 16 + (_nbt.getInteger("z") - chunk.zPosition * 16));
                        }
                    }

                    chunk = this.readChunkFromNBT(worldIn, nbttagcompound);
                }

                Object[] data = new Object[2];
                data[0] = chunk;
                data[1] = p_75822_4_;
                // event is fired in ChunkIOProvider.callStage2 since it must be fired after TE's load.
                // MinecraftForge.EVENT_BUS.post(new ChunkDataEvent.Load(chunk, par4NBTTagCompound));
                return data;
            }
        }
    }

    public void saveChunk(World worldIn, Chunk chunkIn) throws MinecraftException, IOException
    {
        worldIn.checkSessionLock();

        try
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound.setTag("Level", nbttagcompound1);
            nbttagcompound.setInteger("DataVersion", 169);
            this.writeChunkToNBT(chunkIn, worldIn, nbttagcompound1);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkDataEvent.Save(chunkIn, nbttagcompound));
            this.addChunkToPending(chunkIn.getChunkCoordIntPair(), nbttagcompound);
        }
        catch (Exception exception)
        {
            logger.error((String)"Failed to save chunk", (Throwable)exception);
        }
    }

    protected void addChunkToPending(ChunkCoordIntPair pos, NBTTagCompound compound)
    {
        if (!this.pendingAnvilChunksCoordinates.contains(pos))
        {
            this.chunksToRemove.put(pos, compound);
        }

        ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
    }

    /**
     * Returns a boolean stating if the write was unsuccessful.
     */
    public boolean writeNextIO()
    {
        if (this.chunksToRemove.isEmpty())
        {
            if (this.savingExtraData)
            {
                logger.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] {this.chunkSaveLocation.getName()});
            }

            return false;
        }
        else
        {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)this.chunksToRemove.keySet().iterator().next();
            boolean lvt_3_1_;

            try
            {
                this.pendingAnvilChunksCoordinates.add(chunkcoordintpair);
                NBTTagCompound nbttagcompound = (NBTTagCompound)this.chunksToRemove.remove(chunkcoordintpair);

                if (nbttagcompound != null)
                {
                    try
                    {
                        this.writeChunkData(chunkcoordintpair, nbttagcompound);
                    }
                    catch (Exception exception)
                    {
                        logger.error((String)"Failed to save chunk", (Throwable)exception);
                    }
                }

                lvt_3_1_ = true;
            }
            finally
            {
                this.pendingAnvilChunksCoordinates.remove(chunkcoordintpair);
            }

            return lvt_3_1_;
        }
    }

    private void writeChunkData(ChunkCoordIntPair pos, NBTTagCompound compound) throws IOException
    {
        DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, pos.chunkXPos, pos.chunkZPos);
        CompressedStreamTools.write(compound, dataoutputstream);
        dataoutputstream.close();
    }

    /**
     * Save extra data associated with this Chunk not normally saved during autosave, only during chunk unload.
     * Currently unused.
     */
    public void saveExtraChunkData(World worldIn, Chunk chunkIn) throws IOException
    {
    }

    /**
     * Called every World.tick()
     */
    public void chunkTick()
    {
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unused.
     */
    public void saveExtraData()
    {
        try
        {
            this.savingExtraData = true;

            while (true)
            {
                if (this.writeNextIO())
                {
                    continue;
                }
            }
        }
        finally
        {
            this.savingExtraData = false;
        }
    }

    /**
     * Writes the Chunk passed as an argument to the NBTTagCompound also passed, using the World argument to retrieve
     * the Chunk's last update time.
     */
    private void writeChunkToNBT(Chunk chunkIn, World worldIn, NBTTagCompound compound)
    {
        compound.setInteger("xPos", chunkIn.xPosition);
        compound.setInteger("zPos", chunkIn.zPosition);
        compound.setLong("LastUpdate", worldIn.getTotalWorldTime());
        compound.setIntArray("HeightMap", chunkIn.getHeightMap());
        compound.setBoolean("TerrainPopulated", chunkIn.isTerrainPopulated());
        compound.setBoolean("LightPopulated", chunkIn.isLightPopulated());
        compound.setLong("InhabitedTime", chunkIn.getInhabitedTime());
        ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
        NBTTagList nbttaglist = new NBTTagList();
        boolean flag = !worldIn.provider.getHasNoSky();

        for (ExtendedBlockStorage extendedblockstorage : aextendedblockstorage)
        {
            if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Y", (byte)(extendedblockstorage.getYLocation() >> 4 & 255));
                byte[] abyte = new byte[4096];
                NibbleArray nibblearray = new NibbleArray();
                NibbleArray nibblearray1 = extendedblockstorage.getData().getDataForNBT(abyte, nibblearray);
                nbttagcompound.setByteArray("Blocks", abyte);
                nbttagcompound.setByteArray("Data", nibblearray.getData());

                if (nibblearray1 != null)
                {
                    nbttagcompound.setByteArray("Add", nibblearray1.getData());
                }

                nbttagcompound.setByteArray("BlockLight", extendedblockstorage.getBlocklightArray().getData());

                if (flag)
                {
                    nbttagcompound.setByteArray("SkyLight", extendedblockstorage.getSkylightArray().getData());
                }
                else
                {
                    nbttagcompound.setByteArray("SkyLight", new byte[extendedblockstorage.getBlocklightArray().getData().length]);
                }

                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Sections", nbttaglist);
        compound.setByteArray("Biomes", chunkIn.getBiomeArray());
        chunkIn.setHasEntities(false);
        NBTTagList nbttaglist1 = new NBTTagList();

        for (int i = 0; i < chunkIn.getEntityLists().length; ++i)
        {
            for (Entity entity : chunkIn.getEntityLists()[i])
            {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();

                if (entity.writeToNBTOptional(nbttagcompound2))
                {
                    try
                    {
                    chunkIn.setHasEntities(true);
                    nbttaglist1.appendTag(nbttagcompound2);
                    }
                    catch (Exception e)
                    {
                        net.minecraftforge.fml.common.FMLLog.log(org.apache.logging.log4j.Level.ERROR, e,
                                "An Entity type %s has thrown an exception trying to write state. It will not persist. Report this to the mod author",
                                entity.getClass().getName());
                    }
                }
            }
        }

        compound.setTag("Entities", nbttaglist1);
        NBTTagList nbttaglist2 = new NBTTagList();

        for (TileEntity tileentity : chunkIn.getTileEntityMap().values())
        {
            try
            {
            NBTTagCompound nbttagcompound3 = new NBTTagCompound();
            tileentity.writeToNBT(nbttagcompound3);
            nbttaglist2.appendTag(nbttagcompound3);
            }
            catch (Exception e)
            {
                net.minecraftforge.fml.common.FMLLog.log(org.apache.logging.log4j.Level.ERROR, e,
                        "A TileEntity type %s has throw an exception trying to write state. It will not persist. Report this to the mod author",
                        tileentity.getClass().getName());
            }
        }

        compound.setTag("TileEntities", nbttaglist2);
        List<NextTickListEntry> list = worldIn.getPendingBlockUpdates(chunkIn, false);

        if (list != null)
        {
            long j = worldIn.getTotalWorldTime();
            NBTTagList nbttaglist3 = new NBTTagList();

            for (NextTickListEntry nextticklistentry : list)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextticklistentry.getBlock());
                nbttagcompound1.setString("i", resourcelocation == null ? "" : resourcelocation.toString());
                nbttagcompound1.setInteger("x", nextticklistentry.position.getX());
                nbttagcompound1.setInteger("y", nextticklistentry.position.getY());
                nbttagcompound1.setInteger("z", nextticklistentry.position.getZ());
                nbttagcompound1.setInteger("t", (int)(nextticklistentry.scheduledTime - j));
                nbttagcompound1.setInteger("p", nextticklistentry.priority);
                nbttaglist3.appendTag(nbttagcompound1);
            }

            compound.setTag("TileTicks", nbttaglist3);
        }
    }

    /**
     * Reads the data stored in the passed NBTTagCompound and creates a Chunk with that data in the passed World.
     * Returns the created Chunk.
     */
    private Chunk readChunkFromNBT(World worldIn, NBTTagCompound compound)
    {
        int i = compound.getInteger("xPos");
        int j = compound.getInteger("zPos");
        Chunk chunk = new Chunk(worldIn, i, j);
        chunk.setHeightMap(compound.getIntArray("HeightMap"));
        chunk.setTerrainPopulated(compound.getBoolean("TerrainPopulated"));
        chunk.setLightPopulated(compound.getBoolean("LightPopulated"));
        chunk.setInhabitedTime(compound.getLong("InhabitedTime"));
        NBTTagList nbttaglist = compound.getTagList("Sections", 10);
        int k = 16;
        ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[k];
        boolean flag = !worldIn.provider.getHasNoSky();

        for (int l = 0; l < nbttaglist.tagCount(); ++l)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(l);
            int i1 = nbttagcompound.getByte("Y");
            ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(i1 << 4, flag);
            byte[] abyte = nbttagcompound.getByteArray("Blocks");
            NibbleArray nibblearray = new NibbleArray(nbttagcompound.getByteArray("Data"));
            NibbleArray nibblearray1 = nbttagcompound.hasKey("Add", 7) ? new NibbleArray(nbttagcompound.getByteArray("Add")) : null;
            extendedblockstorage.getData().setDataFromNBT(abyte, nibblearray, nibblearray1);
            extendedblockstorage.setBlocklightArray(new NibbleArray(nbttagcompound.getByteArray("BlockLight")));

            if (flag)
            {
                extendedblockstorage.setSkylightArray(new NibbleArray(nbttagcompound.getByteArray("SkyLight")));
            }

            extendedblockstorage.removeInvalidBlocks();
            aextendedblockstorage[i1] = extendedblockstorage;
        }

        chunk.setStorageArrays(aextendedblockstorage);

        if (compound.hasKey("Biomes", 7))
        {
            chunk.setBiomeArray(compound.getByteArray("Biomes"));
        }

        // End this method here and split off entity loading to another method
        return chunk;
    }

    public void loadEntities(World worldIn, NBTTagCompound compound, Chunk chunk)
    {
        NBTTagList nbttaglist1 = compound.getTagList("Entities", 10);

        if (nbttaglist1 != null)
        {
            for (int j1 = 0; j1 < nbttaglist1.tagCount(); ++j1)
            {
                NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(j1);
                readChunkEntity(nbttagcompound1, worldIn, chunk);
                chunk.setHasEntities(true);
            }
        }

        NBTTagList nbttaglist2 = compound.getTagList("TileEntities", 10);

        if (nbttaglist2 != null)
        {
            for (int k1 = 0; k1 < nbttaglist2.tagCount(); ++k1)
            {
                NBTTagCompound nbttagcompound2 = nbttaglist2.getCompoundTagAt(k1);
                TileEntity tileentity = TileEntity.createTileEntity(worldIn.getMinecraftServer(), nbttagcompound2);

                if (tileentity != null)
                {
                    chunk.addTileEntity(tileentity);
                }
            }
        }

        if (compound.hasKey("TileTicks", 9))
        {
            NBTTagList nbttaglist3 = compound.getTagList("TileTicks", 10);

            if (nbttaglist3 != null)
            {
                for (int l1 = 0; l1 < nbttaglist3.tagCount(); ++l1)
                {
                    NBTTagCompound nbttagcompound3 = nbttaglist3.getCompoundTagAt(l1);
                    Block block;

                    if (nbttagcompound3.hasKey("i", 8))
                    {
                        block = Block.getBlockFromName(nbttagcompound3.getString("i"));
                    }
                    else
                    {
                        block = Block.getBlockById(nbttagcompound3.getInteger("i"));
                    }

                    worldIn.scheduleBlockUpdate(new BlockPos(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z")), block, nbttagcompound3.getInteger("t"), nbttagcompound3.getInteger("p"));
                }
            }
        }
    }

    public static Entity readChunkEntity(NBTTagCompound p_186050_0_, World p_186050_1_, Chunk p_186050_2_)
    {
        Entity entity = createEntityFromNBT(p_186050_0_, p_186050_1_);

        if (entity == null)
        {
            return null;
        }
        else
        {
            p_186050_2_.addEntity(entity);

            if (p_186050_0_.hasKey("Passengers", 9))
            {
                NBTTagList nbttaglist = p_186050_0_.getTagList("Passengers", 10);

                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    Entity entity1 = readChunkEntity(nbttaglist.getCompoundTagAt(i), p_186050_1_, p_186050_2_);

                    if (entity1 != null)
                    {
                        entity1.startRiding(entity, true);
                    }
                }
            }

            return entity;
        }
    }

    public static Entity readWorldEntityPos(NBTTagCompound p_186054_0_, World p_186054_1_, double p_186054_2_, double p_186054_4_, double p_186054_6_, boolean p_186054_8_)
    {
        Entity entity = createEntityFromNBT(p_186054_0_, p_186054_1_);

        if (entity == null)
        {
            return null;
        }
        else
        {
            entity.setLocationAndAngles(p_186054_2_, p_186054_4_, p_186054_6_, entity.rotationYaw, entity.rotationPitch);

            if (p_186054_8_ && !p_186054_1_.spawnEntityInWorld(entity))
            {
                return null;
            }
            else
            {
                if (p_186054_0_.hasKey("Passengers", 9))
                {
                    NBTTagList nbttaglist = p_186054_0_.getTagList("Passengers", 10);

                    for (int i = 0; i < nbttaglist.tagCount(); ++i)
                    {
                        Entity entity1 = readWorldEntityPos(nbttaglist.getCompoundTagAt(i), p_186054_1_, p_186054_2_, p_186054_4_, p_186054_6_, p_186054_8_);

                        if (entity1 != null)
                        {
                            entity1.startRiding(entity, true);
                        }
                    }
                }

                return entity;
            }
        }
    }

    protected static Entity createEntityFromNBT(NBTTagCompound p_186053_0_, World p_186053_1_)
    {
        try
        {
            return EntityList.createEntityFromNBT(p_186053_0_, p_186053_1_);
        }
        catch (RuntimeException var3)
        {
            return null;
        }
    }

    public static void spawnEntity(Entity p_186052_0_, World p_186052_1_)
    {
        if (p_186052_1_.spawnEntityInWorld(p_186052_0_) && p_186052_0_.isBeingRidden())
        {
            for (Entity entity : p_186052_0_.getPassengers())
            {
                spawnEntity(entity, p_186052_1_);
            }
        }
    }

    public static Entity readWorldEntity(NBTTagCompound p_186051_0_, World p_186051_1_, boolean p_186051_2_)
    {
        Entity entity = createEntityFromNBT(p_186051_0_, p_186051_1_);

        if (entity == null)
        {
            return null;
        }
        else if (p_186051_2_ && !p_186051_1_.spawnEntityInWorld(entity))
        {
            return null;
        }
        else
        {
            if (p_186051_0_.hasKey("Passengers", 9))
            {
                NBTTagList nbttaglist = p_186051_0_.getTagList("Passengers", 10);

                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    Entity entity1 = readWorldEntity(nbttaglist.getCompoundTagAt(i), p_186051_1_, p_186051_2_);

                    if (entity1 != null)
                    {
                        entity1.startRiding(entity, true);
                    }
                }
            }

            return entity;
        }
    }
}