package io.github.seanboyy.matmod.items;

import net.minecraft.item.ItemSword;

import net.minecraft.item.Item.ToolMaterial;
public class ItemModSword extends ItemSword {

	public ItemModSword(String unlocalizedName, ToolMaterial material) {
		super(material);
		this.func_77655_b(unlocalizedName);
	}
}
