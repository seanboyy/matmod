package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderZombie extends RenderBiped<EntityZombie>
{
    private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");
    private static final ResourceLocation field_188330_l = new ResourceLocation("textures/entity/zombie_villager/zombie_farmer.png");
    private static final ResourceLocation field_188331_m = new ResourceLocation("textures/entity/zombie_villager/zombie_librarian.png");
    private static final ResourceLocation field_188332_n = new ResourceLocation("textures/entity/zombie_villager/zombie_priest.png");
    private static final ResourceLocation field_188333_o = new ResourceLocation("textures/entity/zombie_villager/zombie_smith.png");
    private static final ResourceLocation field_188329_p = new ResourceLocation("textures/entity/zombie_villager/zombie_butcher.png");
    private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    private final ModelBiped defaultModel;
    private ModelZombieVillager zombieVillagerModel;
    private final List<LayerRenderer<EntityZombie>> field_177121_n;
    private final List<LayerRenderer<EntityZombie>> field_177122_o;

    public RenderZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelZombie(), 0.5F, 1.0F);
        LayerRenderer<?> layerrenderer = (LayerRenderer)this.layerRenderers.get(0);
        this.defaultModel = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
        this.field_177122_o = Lists.newArrayList(this.layerRenderers);

        if (layerrenderer instanceof LayerCustomHead)
        {
            this.removeLayer(layerrenderer);
            this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
        }

        this.removeLayer(layerbipedarmor);
        this.addLayer(new LayerVillagerArmor(this));
        this.field_177121_n = Lists.newArrayList(this.layerRenderers);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityZombie entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.func_82427_a(entity);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityZombie entity)
    {
        if (entity.isVillager())
        {
            switch (entity.getVillagerType())
            {
                case 0:
                    return field_188330_l;
                case 1:
                    return field_188331_m;
                case 2:
                    return field_188332_n;
                case 3:
                    return field_188333_o;
                case 4:
                    return field_188329_p;
                default:
                    return zombieVillagerTextures;
            }
        }
        else
        {
            return zombieTextures;
        }
    }

    private void func_82427_a(EntityZombie zombie)
    {
        if (zombie.isVillager())
        {
            this.mainModel = this.zombieVillagerModel;
            this.layerRenderers = this.field_177121_n;
        }
        else
        {
            this.mainModel = this.defaultModel;
            this.layerRenderers = this.field_177122_o;
        }

        this.modelBipedMain = (ModelBiped)this.mainModel;
    }

    protected void rotateCorpse(EntityZombie bat, float p_77043_2_, float p_77043_3_, float partialTicks)
    {
        if (bat.isConverting())
        {
            p_77043_3_ += (float)(Math.cos((double)bat.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }

        super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
    }
}