package io.github.seanboyy.matmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BasicItem extends Item{
	
	public BasicItem(String unlocalizedName) {
		super();
		
		this.func_77655_b(unlocalizedName);
		this.func_77637_a(CreativeTabs.field_78035_l);
	}
}