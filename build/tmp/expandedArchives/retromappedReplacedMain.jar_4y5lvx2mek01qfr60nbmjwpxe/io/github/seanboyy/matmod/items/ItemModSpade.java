package io.github.seanboyy.matmod.items;

import net.minecraft.item.ItemSpade;

import net.minecraft.item.Item.ToolMaterial;
public class ItemModSpade extends ItemSpade{

	public ItemModSpade(String unlocalizedName, ToolMaterial material) {
		super(material);
		this.func_77655_b(unlocalizedName);
	}
}
