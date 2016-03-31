package io.github.seanboyy.matmod.items;

import net.minecraft.item.ItemSpade;

public class ItemModSpade extends ItemSpade{

	public ItemModSpade(String unlocalizedName, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
	}
}
