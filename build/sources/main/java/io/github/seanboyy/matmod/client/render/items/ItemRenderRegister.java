package io.github.seanboyy.matmod.client.render.items;

import io.github.seanboyy.matmod.Main;
import io.github.seanboyy.matmod.items.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public final class ItemRenderRegister {
	
	public static void registerItemRenderer() {
		reg(ModItems.upgradedDiamond);
		reg(ModItems.obsidianIngot);
		reg(ModItems.obsidianRod);
		reg(ModItems.ironRod);
		reg(ModItems.diamondRod);
		reg(ModItems.hybridMix);
		reg(ModItems.hybridIngot);
		reg(ModItems.upgradedDiamondAxe);
		reg(ModItems.upgradedDiamondHoe);
		reg(ModItems.upgradedDiamondPickaxe);
		reg(ModItems.upgradedDiamondShovel);
		reg(ModItems.upgradedDiamondSword);
		reg(ModItems.emeraldAxe);
		reg(ModItems.emeraldHoe);
		reg(ModItems.emeraldPickaxe);
		reg(ModItems.emeraldShovel);
		reg(ModItems.emeraldSword);
		reg(ModItems.obsidianAxe);
		reg(ModItems.obsidianHoe);
		reg(ModItems.obsidianPickaxe);
		reg(ModItems.obsidianShovel);
		reg(ModItems.obsidianSword);
		reg(ModItems.hybridAxe);
		reg(ModItems.hybridHoe);
		reg(ModItems.hybridPickaxe);
		reg(ModItems.hybridShovel);
		reg(ModItems.hybridSword);
		reg(ModItems.upgradedDiamondHelmet);
		reg(ModItems.upgradedDiamondChestplate);
		reg(ModItems.upgradedDiamondLeggings);
		reg(ModItems.upgradedDiamondBoots);
		reg(ModItems.emeraldHelmet);
		reg(ModItems.emeraldChestplate);
		reg(ModItems.emeraldLeggings);
		reg(ModItems.emeraldBoots);
		reg(ModItems.obsidianHelmet);
		reg(ModItems.obsidianChestplate);
		reg(ModItems.obsidianLeggings);
		reg(ModItems.obsidianBoots);
		reg(ModItems.hybridHelmet);
		reg(ModItems.hybridChestplate);
		reg(ModItems.hybridLeggings);
		reg(ModItems.hybridBoots);
	}

	public static String modid = Main.MODID;
	
	public static void reg(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
