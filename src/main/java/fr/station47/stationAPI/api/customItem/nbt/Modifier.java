package fr.station47.stationAPI.api.customItem.nbt;

/**
 * Wrapper to apply NBT modification to an itemStack
 */
public class Modifier {

    private String type;
    private String slot;
    private double modifier;
    private int operation;

    /**
     *
     * @param type NBT atributesModifier value
     * @see AttributesType
     * @param slot NBT Slot value
     * @see SlotType
     * @param modifier NBT modifier value
     * @see Modifier
     * @param operation NBT operation
     * @see Operation
     */
    public Modifier(String type, String slot, double modifier, int operation) {
        this.type = type;
        this.slot = slot;
        this.modifier = modifier;
    }

    public String getType() {
        return type;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot){
        this.slot = slot;
    }

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier){
        this.modifier = modifier;
    }

    public int getOperation(){
        return operation;
    }
}
