package io.github.seanboyy.matmod.items;

import net.minecraft.item.ItemTool;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import net.minecraft.item.Item.ToolMaterial;
public class ItemModAxe extends ItemTool{
	
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.field_150344_f, Blocks.field_150342_X, Blocks.field_150364_r, Blocks.field_150363_s, Blocks.field_150486_ae, Blocks.field_150423_aK, Blocks.field_150428_aP, Blocks.field_150440_ba, Blocks.field_150468_ap, Blocks.field_150471_bO, Blocks.field_150452_aw});
	
	public ItemModAxe(String unlocalizedName, ToolMaterial material, float damage, float speed) {
		super(damage, speed, material, EFFECTIVE_ON);
		this.func_77655_b(unlocalizedName);
	}
}
