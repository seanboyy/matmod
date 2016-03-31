package io.github.seanboyy.matmod.crafting;

import io.github.seanboyy.matmod.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {

	public static void initCrafting(){
		GameRegistry.addSmelting(Blocks.field_150484_ah, new ItemStack(ModItems.upgradedDiamond, 6), 1.0F);
		GameRegistry.addRecipe(new ItemStack(ModItems.ironRod), new Object[] {"#", "#", '#', Items.field_151042_j});
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondRod), new Object[] {"#", "#", '#', Items.field_151045_i});
		GameRegistry.addSmelting(Blocks.field_150343_Z, new ItemStack(ModItems.obsidianIngot),1.0F);
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianRod),new Object[] {"#", "#", '#', ModItems.obsidianIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondAxe), new Object[] {"##", "#X", " X", '#', ModItems.upgradedDiamond, 'X', ModItems.ironRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondAxe), new Object[] {"##", "X#", "X ", '#', ModItems.upgradedDiamond, 'X', ModItems.ironRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondHoe), new Object[] {"##", " X", " X", '#', ModItems.upgradedDiamond, 'X', ModItems.ironRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondHoe), new Object[] {"##", "X ", "X ", '#', ModItems.upgradedDiamond, 'X', ModItems.ironRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondPickaxe), new Object[] {"###", " X ", " X ", '#', ModItems.upgradedDiamond, 'X', ModItems.ironRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondShovel), new Object[] {"#", "X", "X", '#', ModItems.upgradedDiamond, 'X', ModItems.ironRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondSword), new Object[] {"#", "#", "X", '#', ModItems.upgradedDiamond, 'X', ModItems.ironRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldAxe), new Object[] {"#@", "#X", " X", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldAxe), new Object[] {"@#", "X#", "X ", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldHoe), new Object[] {"@#", " X", " X", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldHoe), new Object[] {"#@", "X ", "X ", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldPickaxe), new Object[] {"#@#", " X ", " X ", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldShovel), new Object[] {"#", "X", "X", '#', Items.field_151166_bC, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldSword), new Object[] {"#", "@", "X", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianAxe), new Object[] {"##", "#X", " X", '#', ModItems.obsidianIngot, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianAxe), new Object[] {"##", "X#", "X ", '#', ModItems.obsidianIngot, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianHoe), new Object[] {"##", " X", " X", '#', ModItems.obsidianIngot, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianHoe), new Object[] {"##", "X ", "X ", '#', ModItems.obsidianIngot, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianPickaxe), new Object[] {"###", " X ", " X ", '#', ModItems.obsidianIngot, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianShovel), new Object[] {"#", "X", "X", '#', ModItems.obsidianIngot, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianSword), new Object[] {"#", "#", "X", '#', ModItems.obsidianIngot, 'X', ModItems.diamondRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridMix, 2), new Object[] {"@#@", "$N$", "@#@", '@', ModItems.upgradedDiamond, '#', ModItems.obsidianIngot, '$', Items.field_151166_bC, 'N', Blocks.field_150484_ah});
		GameRegistry.addSmelting(ModItems.hybridMix, new ItemStack(ModItems.hybridIngot), 3.0F);
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridAxe), new Object[] {"##", "#X", " X", '#', ModItems.hybridIngot, 'X', ModItems.obsidianRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridAxe), new Object[] {"##", "X#", "X ", '#', ModItems.hybridIngot, 'X', ModItems.obsidianRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridHoe), new Object[] {"##", " X", " X", '#', ModItems.hybridIngot, 'X', ModItems.obsidianRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridHoe), new Object[] {"##", "X ", "X ", '#', ModItems.hybridIngot, 'X', ModItems.obsidianRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridPickaxe), new Object[] {"###", " X ", " X ", '#', ModItems.hybridIngot, 'X', ModItems.obsidianRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridShovel), new Object[] {"#", "X", "X", '#', ModItems.hybridIngot, 'X', ModItems.obsidianRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridSword), new Object[] {"#", "#", "X", '#', ModItems.hybridIngot, 'X', ModItems.obsidianRod});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondHelmet), new Object[] {"###", "# #", '#', ModItems.upgradedDiamond});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondChestplate), new Object[] {"# #", "###", "###", '#', ModItems.upgradedDiamond});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondLeggings), new Object[] {"###", "# #", "# #", '#', ModItems.upgradedDiamond});
		GameRegistry.addRecipe(new ItemStack(ModItems.upgradedDiamondBoots), new Object[] {"# #", "# #", '#', ModItems.upgradedDiamond});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldHelmet), new Object[] {"#@#", "# #", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldChestplate), new Object[] {"# #", "#@#", "#@#", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldLeggings), new Object[] {"#@#", "# #", "# #", '#', Items.field_151166_bC, '@', Blocks.field_150475_bE});
		GameRegistry.addRecipe(new ItemStack(ModItems.emeraldBoots), new Object[] {"# #", "# #", '#', Items.field_151166_bC});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianHelmet), new Object[] {"###", "# #", '#', ModItems.obsidianIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianChestplate), new Object[] {"# #", "###", "###", '#', ModItems.obsidianIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianLeggings), new Object[] {"###", "# #", "# #", '#', ModItems.obsidianIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.obsidianBoots), new Object[] {"# #", "# #", '#', ModItems.obsidianIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridHelmet), new Object[] {"###", "# #", '#', ModItems.hybridIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridChestplate), new Object[] {"# #", "###", "###", '#', ModItems.hybridIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridLeggings), new Object[] {"###", "# #", "# #", '#', ModItems.hybridIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.hybridBoots), new Object[] {"# #", "# #", '#', ModItems.hybridIngot});
	}
}
