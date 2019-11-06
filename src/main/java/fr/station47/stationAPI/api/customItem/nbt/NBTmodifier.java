package fr.station47.stationAPI.api.customItem.nbt;

import de.tr7zw.changeme.nbtapi.*;
import fr.station47.stationAPI.api.Utils;
import org.bukkit.inventory.ItemStack;

public class NBTmodifier {


    /**
     * Sets the modifiers value on the itemstack
     * @param item
     * @param modifiers
     * @return
     */
    public static ItemStack set(ItemStack item, Modifier... modifiers){
        NBTItem nbtItem = new NBTItem(item);
        NBTCompoundList attributes = nbtItem.getCompoundList("AttributeModifiers");
        for (Modifier modifier:modifiers){
            NBTListCompound mod = attributes.addCompound();
            mod.setString("AttributeName", modifier.getType());
            mod.setString("Name", modifier.getType());
            mod.setInteger("Operation",modifier.getOperation());
            mod.setDouble("Amount", modifier.getModifier());
            mod.setString("Slot", modifier.getSlot());
            mod.setInteger("UUIDLeast", 59764);
            mod.setInteger("UUIDMost", 31483);
        }
        return nbtItem.getItem();
    }

    /**
     * Modify the item relatively to its current NBT values.
     * @param item item to apply the NBT modifications to
     * @param modifiers Modifiers to apply
     * @return The itemStack with the applied modifiers
     */
    public static ItemStack modifyRelative(ItemStack item, Modifier... modifiers){
        NBTItem nbtItem = new NBTItem(item);
        NBTCompoundList attributes = nbtItem.getCompoundList("AttributeModifiers");
        if (attributes.size() == 0){
            nbtItem = new NBTItem(prepareDefault(item));
            attributes = nbtItem.getCompoundList("AttributeModifiers");
        }
        for (Modifier modifier:modifiers) {
            boolean foundOld = false;
            for (int i = 0; i < attributes.size(); i++) {
                if (attributes.get(i).getString("AttributeName").equals(modifier.getType())) {
                    NBTListCompound old = attributes.get(i);
                    old.setDouble("Amount", old.getDouble("Amount") + modifier.getModifier());
                    foundOld = true;
                    break;
                }
            }
            if (!foundOld) {
                NBTListCompound mod = attributes.addCompound();
                mod.setString("AttributeName", modifier.getType());
                mod.setString("Name", modifier.getType());
                mod.setInteger("Operation", modifier.getOperation());
                if (modifier.getType().equals(AttributesType.ATTACK_DAMAGE)) {
                    mod.setDouble("Amount", getDefaultAttackDamage(item) + modifier.getModifier());
                    NBTListCompound speedAttack = attributes.addCompound();
                    speedAttack.setString("AttributeName", AttributesType.ATTACK_SPEED);
                    speedAttack.setString("Name", AttributesType.ATTACK_SPEED);
                    speedAttack.setInteger("Operation", 0);
                    speedAttack.setDouble("Amount", getDefaultSpeedAttack(item));
                    speedAttack.setString("Slot", SlotType.MAINHAND);
                    speedAttack.setInteger("UUIDLeast", 59764);
                    speedAttack.setInteger("UUIDMost", 31483);

                } else {
                    mod.setDouble("Amount", modifier.getModifier());
                }
                mod.setString("Slot", modifier.getSlot());
                mod.setInteger("UUIDLeast", 59764);
                mod.setInteger("UUIDMost", 31483);
            }
        }
        return nbtItem.getItem();
    }

    /**
     * Adds default value as NBT Tags so later usage of modify relative works correctly
     * @param item stack to apply the NBT modifications to
     * @return ItemStack with NBT value equals to its default value
     */
    public static ItemStack prepareDefault(ItemStack item){
        NBTItem nbtItem = new NBTItem(item);
        NBTCompoundList attributes = nbtItem.getCompoundList("AttributeModifiers");
        if (attributes.size() == 0){
            double def;
            if ((def = getDefaultAttackDamage(item))!=0){
                NBTListCompound mod = attributes.addCompound();
                mod.setString("AttributeName", AttributesType.ATTACK_DAMAGE);
                mod.setString("Name", AttributesType.ATTACK_DAMAGE);
                mod.setInteger("Operation",0);
                mod.setDouble("Amount", def);
                mod.setString("Slot", SlotType.MAINHAND);
                mod.setInteger("UUIDLeast", 59764);
                mod.setInteger("UUIDMost", 31483);
            }
            if ((def = getDefaultSpeedAttack(item))!= 0){
                NBTListCompound mod = attributes.addCompound();
                mod.setString("AttributeName", AttributesType.ATTACK_SPEED);
                mod.setString("Name", AttributesType.ATTACK_SPEED);
                mod.setInteger("Operation",0);
                mod.setDouble("Amount", def);
                mod.setString("Slot", SlotType.MAINHAND);
                mod.setInteger("UUIDLeast", 59764);
                mod.setInteger("UUIDMost", 31483);
            }
            if ((def = getDefaultArmor(item))!=0){
                String slot;
                if (Utils.isHelmet(item)){
                    slot = SlotType.HEAD;
                } else if (Utils.isChestplate(item)){
                    slot = SlotType.CHEST;
                } else if (Utils.isLeggings(item)){
                    slot = SlotType.LEGS;
                } else if (Utils.isBoot(item)){
                    slot = SlotType.FEET;
                } else {
                    slot = "";
                }
                NBTListCompound mod = attributes.addCompound();
                mod.setString("AttributeName", AttributesType.ARMOR);
                mod.setString("Name", AttributesType.ARMOR);
                mod.setInteger("Operation",0);
                mod.setDouble("Amount", def);
                mod.setString("Slot", slot);
                mod.setInteger("UUIDLeast", 59764);
                mod.setInteger("UUIDMost", 31483);
            }
            if ((def = getDefaultToughness(item))!=0){
                String slot;
                if (Utils.isHelmet(item)){
                    slot = SlotType.HEAD;
                } else if (Utils.isChestplate(item)){
                    slot = SlotType.CHEST;
                } else if (Utils.isLeggings(item)){
                    slot = SlotType.LEGS;
                } else if (Utils.isBoot(item)){
                    slot = SlotType.FEET;
                } else {
                    slot = "";
                }
                NBTListCompound mod = attributes.addCompound();
                mod.setString("AttributeName", AttributesType.ARMOR_TOUGHNESS);
                mod.setString("Name", AttributesType.ARMOR);
                mod.setInteger("Operation",0);
                mod.setDouble("Amount", def);
                mod.setString("Slot", slot);
                mod.setInteger("UUIDLeast", 59764);
                mod.setInteger("UUIDMost", 31483);
            }
        }
        return nbtItem.getItem();
    }

    public static double getDefaultAttackDamage(ItemStack itemStack){
        switch (itemStack.getType()){
            case WOODEN_AXE:
                return 6;
            case GOLDEN_AXE:
                return 6;
            case STONE_AXE:
                return 8;
            case IRON_AXE:
                return 8;
            case DIAMOND_AXE:
                return 8;
            case GOLDEN_SWORD:
                return 3;
            case WOODEN_SWORD:
                return 3;
            case STONE_SWORD:
                return 4;
            case IRON_SWORD:
                return 5;
            case DIAMOND_SWORD:
                return 6;
            case WOODEN_SHOVEL:
                return 1.5;
            case WOODEN_PICKAXE:
                return 1;
            case STONE_SHOVEL:
                return 2.5;
            case STONE_PICKAXE:
                return 3;
            case IRON_SHOVEL:
                return 3.5;
            case IRON_PICKAXE:
                return 3;
            case GOLDEN_SHOVEL:
                return 1.5;
            case GOLDEN_PICKAXE:
                return 1;
            case DIAMOND_SHOVEL:
                return 4.5;
            case DIAMOND_PICKAXE:
                return 4;
            default:
                return 0;
        }
    }

    public static double getDefaultSpeedAttack(ItemStack itemStack){
        if (Utils.isSword(itemStack)){
            return -2.4;
        }
        switch (itemStack.getType()) {
            case WOODEN_AXE:
                return -3.2;
            case GOLDEN_AXE:
                return -3;
            case STONE_AXE:
                return -3.2;
            case IRON_AXE:
                return -3.1;
            case DIAMOND_AXE:
                return -3;
            case WOODEN_HOE:
                return -3;
            case STONE_HOE:
                return -2;
            case IRON_HOE:
                return -1;
            case GOLDEN_HOE:
                return -3;
            case DIAMOND_HOE:
                return 0;
        }
        if (Utils.isShovel(itemStack)){
            return -3;
        }
        if (Utils.isPickaxe(itemStack)){
            return -2.8;
        }
        return 0;
    }

    public static double getDefaultArmor(ItemStack item){
        switch (item.getType()){
            case LEATHER_HELMET:
                return 1;
            case IRON_HELMET:
                return 2;
            case CHAINMAIL_HELMET:
                return 2;
            case GOLDEN_HELMET:
                return 2;
            case DIAMOND_HELMET:
                return 3;
            case LEATHER_CHESTPLATE:
                return 3;
            case IRON_CHESTPLATE:
                return 6;
            case CHAINMAIL_CHESTPLATE:
                return 5;
            case GOLDEN_CHESTPLATE:
                return 5;
            case DIAMOND_CHESTPLATE:
                return 8;
            case LEATHER_LEGGINGS:
                return 2;
            case IRON_LEGGINGS:
                return 5;
            case CHAINMAIL_LEGGINGS:
                return 4;
            case DIAMOND_LEGGINGS:
                return 6;
            case GOLDEN_LEGGINGS:
                return 3;
            case LEATHER_BOOTS:
                return 1;
            case IRON_BOOTS:
                return 2;
            case CHAINMAIL_BOOTS:
                return 1;
            case GOLDEN_BOOTS:
                return 1;
            case DIAMOND_BOOTS:
                return 3;
            default:
                return 0;
        }
    }

    public static double getDefaultToughness(ItemStack item){
        switch (item.getType()){
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
                return 2;
            default:
                return 0;
        }
    }
}
