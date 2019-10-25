package fr.station47.stationAPI.api.upgrade;

import fr.station47.stationAPI.api.Utils;
import fr.station47.stationAPI.api.customItem.nbt.Modifier;
import fr.station47.stationAPI.api.customItem.nbt.NBTmodifier;
import fr.station47.stationAPI.api.customItem.nbt.SlotType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class ArmorUpgrade extends Upgrade {
    public ArmorUpgrade(String name, String displayName, List<String> description) {
        super(name, displayName, description);
    }

    @Override
    public ItemStack modify(ItemStack item, int level){
        if (Utils.isHelmet(item)){
            for (Modifier modifier: getModifier(SlotType.HEAD,level)){
                item = NBTmodifier.modifyRelative(item,modifier);
            }
            return item;
        } else if (Utils.isChestplate(item)){
            for (Modifier modifier: getModifier(SlotType.CHEST,level)){
                item = NBTmodifier.modifyRelative(item,modifier);
            }
            return item;
        } else if (Utils.isLeggings(item)){
            for (Modifier modifier: getModifier(SlotType.LEGS,level)){
                item = NBTmodifier.modifyRelative(item,modifier);
            }
            return item;
        } else {
            for (Modifier modifier: getModifier(SlotType.FEET,level)){
                item = NBTmodifier.modifyRelative(item,modifier);
            }
            return item;
        }
    }

    protected abstract List<Modifier> getModifier(String slot, int level);

    @Override
    public boolean applicableOn(ItemStack item){
        return Utils.isArmor(item);
    }
}
