package io.github.seanboyy.matmod.items;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {
	
	public static Item tutorialItem;
	public static Item upgradedDiamondPickaxe;
	public static Item upgradedDiamondAxe;
	public static Item upgradedDiamondHoe;
	public static Item upgradedDiamondShovel;
	public static Item upgradedDiamondSword;
	public static Item emeraldPickaxe;
	public static Item emeraldAxe;
	public static Item emeraldHoe;
	public static Item emeraldShovel;
	public static Item emeraldSword;
	public static Item obsidianPickaxe;
	public static Item obsidianAxe;
	public static Item obsidianHoe;
	public static Item obsidianShovel;
	public static Item obsidianSword;
	public static Item hybridPickaxe;
	public static Item hybridAxe;
	public static Item hybridHoe;
	public static Item hybridShovel;
	public static Item hybridSword;
	public static Item upgradedDiamond;
	public static Item hybridMix;
	public static Item hybridIngot;
	public static Item ironRod;
	public static Item diamondRod;
	public static Item obsidianRod;
	public static Item obsidianIngot;
	public static Item upgradedDiamondHelmet;
	public static Item upgradedDiamondChestplate;
	public static Item upgradedDiamondLeggings;
	public static Item upgradedDiamondBoots;
	public static Item emeraldHelmet;
	public static Item emeraldChestplate;
	public static Item emeraldLeggings;
	public static Item emeraldBoots;
	public static Item obsidianHelmet;
	public static Item obsidianChestplate;
	public static Item obsidianLeggings;
	public static Item obsidianBoots;
	public static Item hybridHelmet;
	public static Item hybridChestplate;
	public static Item hybridLeggings;
	public static Item hybridBoots;
	
	public static ToolMaterial UPGRADED_DIAMOND = EnumHelper.addToolMaterial("UPGRADED_DIAMOND", 3, 2250, 10.0F, 4.5F, 15);
	public static ToolMaterial EMERALD = EnumHelper.addToolMaterial("EMERALD", 3, 2000, 11.0F, 5.0F, 15);
	public static ToolMaterial OBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 3, 3500, 12.0F, 6.0F, 13);
	public static ToolMaterial HYBRID = EnumHelper.addToolMaterial("HYBRID", 4, 5000, 16.0F, 7.0F, 20);
	
	public static ArmorMaterial UPGRADED_DIAMONDA = EnumHelper.addArmorMaterial("UPGRADED_DIAMOND", "matmod:upgraded_diamond_armor", 38, new int[] {3,8,6,3}, 15, SoundEvents.item_armor_equip_diamond);
	public static ArmorMaterial EMERALDA = EnumHelper.addArmorMaterial("EMERALD", "matmod:emerald_armor", 40, new int[] {3,8,6,3}, 20, SoundEvents.item_armor_equip_diamond);
	public static ArmorMaterial OBSIDIANA = EnumHelper.addArmorMaterial("OBSIDIAN", "matmod:obsidian_armor", 43, new int[] {3,8,6,3}, 18, SoundEvents.item_armor_equip_diamond);
	public static ArmorMaterial HYBRIDA = EnumHelper.addArmorMaterial("HYBRID", "matmod:hybrid_armor", 50, new int[] {5,10,8,5}, 25, SoundEvents.item_armor_equip_diamond);
	
	public static void createItems() {
		GameRegistry.registerItem(upgradedDiamond = new BasicItem("upgraded_diamond"), "upgraded_diamond");
		GameRegistry.registerItem(obsidianIngot = new BasicItem("obsidian_ingot"), "obsidian_ingot");
		GameRegistry.registerItem(hybridMix = new BasicItem("hybrid_mix"), "hybrid_mix");
		GameRegistry.registerItem(hybridIngot = new BasicItem("hybrid_ingot"), "hybrid_ingot");
		GameRegistry.registerItem(ironRod = new BasicItem("iron_rod"), "iron_rod");
		GameRegistry.registerItem(diamondRod = new BasicItem("diamond_rod"), "diamond_rod");
		GameRegistry.registerItem(obsidianRod = new BasicItem("obsidian_rod"), "obsidian_rod");
		GameRegistry.registerItem(upgradedDiamondPickaxe = new ItemModPickaxe("upgraded_diamond_pickaxe", UPGRADED_DIAMOND), "upgraded_diamond_pickaxe");
		GameRegistry.registerItem(upgradedDiamondAxe = new ItemModAxe("upgraded_diamond_axe", UPGRADED_DIAMOND, 8.0F, -2.9F), "upgraded_diamond_axe");
		GameRegistry.registerItem(upgradedDiamondHoe = new ItemModHoe("upgraded_diamond_hoe", UPGRADED_DIAMOND), "upgraded_diamond_hoe");
		GameRegistry.registerItem(upgradedDiamondShovel = new ItemModSpade("upgraded_diamond_shovel", UPGRADED_DIAMOND), "upgraded_diamond_shovel");
		GameRegistry.registerItem(upgradedDiamondSword = new ItemModSword("upgraded_diamond_sword", UPGRADED_DIAMOND), "upgraded_diamond_sword");
		GameRegistry.registerItem(emeraldPickaxe = new ItemModPickaxe("emerald_pickaxe", EMERALD), "emerald_pickaxe");
		GameRegistry.registerItem(emeraldAxe = new ItemModAxe("emerald_axe", EMERALD, 8.5F, -2.8F), "emerald_axe");
		GameRegistry.registerItem(emeraldHoe = new ItemModHoe("emerald_hoe", EMERALD), "emerald_hoe");
		GameRegistry.registerItem(emeraldShovel = new ItemModSpade("emerald_shovel", EMERALD), "emerald_shovel");
		GameRegistry.registerItem(emeraldSword = new ItemModSword("emerald_sword", EMERALD), "emerald_sword");
		GameRegistry.registerItem(obsidianPickaxe = new ItemModPickaxe("obsidian_pickaxe", OBSIDIAN), "obsidian_pickaxe");
		GameRegistry.registerItem(obsidianAxe = new ItemModAxe("obsidian_axe", OBSIDIAN, 9.0F, -2.7F), "obsidian_axe");
		GameRegistry.registerItem(obsidianHoe = new ItemModHoe("obsidian_hoe", OBSIDIAN), "obsidian_hoe");
		GameRegistry.registerItem(obsidianShovel = new ItemModSpade("obsidian_shovel", OBSIDIAN), "obsidian_shovel");
		GameRegistry.registerItem(obsidianSword = new ItemModSword("obsidian_sword", OBSIDIAN), "obsidian_sword");
		GameRegistry.registerItem(hybridPickaxe = new ItemModPickaxe("hybrid_pickaxe", HYBRID), "hybrid_pickaxe");
		GameRegistry.registerItem(hybridAxe = new ItemModAxe("hybrid_axe", HYBRID, 10.0F, -2.5F), "hybrid_axe");
		GameRegistry.registerItem(hybridHoe = new ItemModHoe("hybrid_hoe", HYBRID), "hybrid_hoe");
		GameRegistry.registerItem(hybridShovel = new ItemModSpade("hybrid_shovel", HYBRID), "hybrid_shovel");
		GameRegistry.registerItem(hybridSword = new ItemModSword("hybrid_sword", HYBRID), "hybrid_sword");
		GameRegistry.registerItem(upgradedDiamondHelmet = new ItemModArmor("upgraded_diamond_helmet", UPGRADED_DIAMONDA, 1, EntityEquipmentSlot.HEAD), "upgraded_diamond_helmet");
		GameRegistry.registerItem(upgradedDiamondChestplate = new ItemModArmor("upgraded_diamond_chestplate", UPGRADED_DIAMONDA, 1, EntityEquipmentSlot.CHEST),  "upgraded_diamond_chestplate");
		GameRegistry.registerItem(upgradedDiamondLeggings = new ItemModArmor("upgraded_diamond_leggings", UPGRADED_DIAMONDA, 2, EntityEquipmentSlot.LEGS),"upgraded_diamond_leggings");
		GameRegistry.registerItem(upgradedDiamondBoots = new ItemModArmor("upgraded_diamond_boots", UPGRADED_DIAMONDA, 1, EntityEquipmentSlot.FEET),"upgraded_diamond_boots");
		GameRegistry.registerItem(emeraldHelmet = new ItemModArmor("emerald_helmet", EMERALDA, 1, EntityEquipmentSlot.HEAD), "emerald_helmet");
		GameRegistry.registerItem(emeraldChestplate = new ItemModArmor("emerald_chestplate", EMERALDA, 1, EntityEquipmentSlot.CHEST),  "emerald_chestplate");
		GameRegistry.registerItem(emeraldLeggings = new ItemModArmor("emerald_leggings", EMERALDA, 2, EntityEquipmentSlot.LEGS),"emerald_leggings");
		GameRegistry.registerItem(emeraldBoots = new ItemModArmor("emerald_boots", EMERALDA, 1, EntityEquipmentSlot.FEET),"emerald_boots");
		GameRegistry.registerItem(obsidianHelmet = new ItemModArmor("obsidian_helmet", OBSIDIANA, 1, EntityEquipmentSlot.HEAD), "obsidian_helmet");
		GameRegistry.registerItem(obsidianChestplate = new ItemModArmor("obsidian_chestplate", OBSIDIANA, 1, EntityEquipmentSlot.CHEST),  "obsidian_chestplate");
		GameRegistry.registerItem(obsidianLeggings = new ItemModArmor("obsidian_leggings", OBSIDIANA, 2, EntityEquipmentSlot.LEGS),"obsidian_leggings");
		GameRegistry.registerItem(obsidianBoots = new ItemModArmor("obsidian_boots", OBSIDIANA, 1, EntityEquipmentSlot.FEET),"obsidian_boots");
		GameRegistry.registerItem(hybridHelmet = new ItemModArmor("hybrid_helmet", HYBRIDA, 1, EntityEquipmentSlot.HEAD), "hybrid_helmet");
		GameRegistry.registerItem(hybridChestplate = new ItemModArmor("hybrid_chestplate", HYBRIDA, 1, EntityEquipmentSlot.CHEST),  "hybrid_chestplate");
		GameRegistry.registerItem(hybridLeggings = new ItemModArmor("hybrid_leggings", HYBRIDA, 2, EntityEquipmentSlot.LEGS),"hybrid_leggings");
		GameRegistry.registerItem(hybridBoots = new ItemModArmor("hybrid_boots", HYBRIDA, 1, EntityEquipmentSlot.FEET),"hybrid_boots");
	}
}
