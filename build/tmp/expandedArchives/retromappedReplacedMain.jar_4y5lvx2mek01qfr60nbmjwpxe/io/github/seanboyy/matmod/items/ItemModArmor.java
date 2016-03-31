package io.github.seanboyy.matmod.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

import net.minecraft.item.ItemArmor.ArmorMaterial;
public class ItemModArmor extends ItemArmor{
	
	public ItemModArmor(String unlocalizedName, ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot){
		super(material, renderIndex, slot);
		
		this.func_77655_b(unlocalizedName);
	}
}
