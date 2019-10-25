package fr.station47.stationAPI.api;

import fr.station47.theme.Theme;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    private static int instance = 0;

    public static String fill(String s, String... args){
        for (int i= 0; i< args.length; i++){
            s = s.replace("{"+i+"}",args[i]);
        }
        return s;
    }

    public static boolean isInt(String s){
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public static String millisToHumanTime(String format, long millis){
        long timeLeft = millis;
        DecimalFormat decimalFormat = new DecimalFormat("#");
        double timeLeftSeconds = timeLeft/1000;
        double daysLeft = Math.floor(timeLeftSeconds/86400);
        double hoursLeft = Math.floor((timeLeftSeconds-daysLeft*86400)/3600);
        double minutesLeft = Math.floor((timeLeftSeconds-daysLeft*86400-hoursLeft*3600)/60);
        double secondsLeft = Math.floor((timeLeftSeconds-daysLeft*86400-hoursLeft*3600-minutesLeft*60));
        return format.replace("%d",String.valueOf(daysLeft))
                .replace("%h", decimalFormat.format(hoursLeft))
                .replace("%m", decimalFormat.format(minutesLeft))
                .replace("%s", decimalFormat.format(secondsLeft));
    }

    public static int romanToInt(String rom){
        switch (rom){
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
            case "VII":
                return 7;
            case "VIII":
                return 8;
            case "IX":
                return 9;
            case "X":
                return 10;
        }
        if (isNumber(rom)){
            return Integer.valueOf(rom);
        } else {
            return 0;
        }
    }

    public static String intToRom(int i){
        switch (i){
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
        }
        return String.valueOf(i);
    }

    public static String getInvisibleKey(){
        instance++;
        int temp = instance;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; temp>1; i++){
            sb.append("&");
            int dig = (temp%(int)Math.pow(10,i));
            sb.append(dig);
            temp-=dig;
        }
        return ChatColor.translateAlternateColorCodes('&',sb.toString());
    }

    public static List<String> whiteLore(String... strings){
        List<String> list = new ArrayList<>(strings.length);
        for (String s: strings){
            list.add(ChatColor.WHITE+s);
        }
        return list;
    }

    public static boolean isWeapon(ItemStack item){
        if (item!=null) {
            switch (item.getType()) {
                case WOODEN_SWORD:
                case STONE_SWORD:
                case IRON_SWORD:
                case GOLDEN_SWORD:
                case DIAMOND_SWORD:
                case BOW:
                    return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isSpade(ItemStack item){
        return isShovel(item);
    }

    public static boolean isShovel(ItemStack item){
        switch (item.getType()){
            case WOODEN_SHOVEL:
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case GOLDEN_SHOVEL:
            case DIAMOND_SHOVEL:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPickaxe(ItemStack item){
        switch (item.getType()){
            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE:
            case DIAMOND_PICKAXE:
                return true;
            default:
                return false;

        }
    }

    public static boolean isAxe(ItemStack item){
        switch (item.getType()){
            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
                return true;
            default:
                return false;
        }
    }
    public static boolean isHoe(ItemStack item){
        switch (item.getType()){
            case WOODEN_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLDEN_HOE:
            case DIAMOND_HOE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTool(ItemStack item){
        return isSpade(item) || isPickaxe(item) || isAxe(item) || isHoe(item);
    }

    public static boolean isSword(ItemStack item){
        switch (item.getType()) {
            case WOODEN_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLDEN_SWORD:
            case DIAMOND_SWORD:
                return true;
            default:
                return false;
        }
    }
    public static boolean isHelmet(ItemStack itemStack){
        switch (itemStack.getType()){
            case LEATHER_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case GOLDEN_HELMET:
            case DIAMOND_HELMET:
                return true;
            default:
                return false;
        }
    }

    public static boolean isChestplate(ItemStack item){
        switch (item.getType()){
            case LEATHER_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case GOLDEN_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
                return true;
            default:
                return false;

        }
    }

    public static boolean isLeggings(ItemStack item){
        switch (item.getType()){
            case LEATHER_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case GOLDEN_LEGGINGS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isBoot(ItemStack item){
        switch (item.getType()){
            case LEATHER_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case GOLDEN_BOOTS:
            case DIAMOND_BOOTS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isArmor(ItemStack item){
        return isHelmet(item) || isChestplate(item) || isLeggings(item) || isBoot(item);
    }

    public static boolean isOre(Block material){
        switch (material.getType()){
            case COAL_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
                return true;
        }
        return false;
    }

    public static boolean isNumber(String number){
        String decimalPattern = "([0-9]*\\.[0-9]+|[0-9]+)";
        return Pattern.matches(decimalPattern, number);
    }
}
