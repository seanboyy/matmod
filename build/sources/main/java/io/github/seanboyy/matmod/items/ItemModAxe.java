package io.github.seanboyy.matmod.items;

import net.minecraft.item.ItemTool;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemModAxe extends ItemTool{
	
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder, Blocks.wooden_button, Blocks.wooden_pressure_plate});
	
	public ItemModAxe(String unlocalizedName, ToolMaterial material, float damage, float speed) {
		super(damage, speed, material, EFFECTIVE_ON);
		this.setUnlocalizedName(unlocalizedName);
	}
}
