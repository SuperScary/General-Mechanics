package general.mechanics.api.electrical.transistor;

public enum TransistorType {

    NPN_BJT("NPN Bipolar Junction Transistor", "NPN", "BJT", "General-purpose amplifier or switch"),
    PNP_BJT("PNP Bipolar Junction Transistor", "PNP", "BJT", "Complementary to NPN, current sourcing"),
    N_CHANNEL_MOSFET("N-Channel MOSFET", "NMOS", "MOSFET", "Switching or low-side power control"),
    P_CHANNEL_MOSFET("P-Channel MOSFET", "PMOS", "MOSFET", "High-side switching applications"),
    N_CHANNEL_JFET("N-Channel JFET", "NJFET", "JFET", "Voltage-controlled resistor, low noise"),
    P_CHANNEL_JFET("P-Channel JFET", "PJFET", "JFET", "Complementary JFET for analog circuits"),
    DARLINGTON_NPN("Darlington NPN", "NPN-DARL", "BJT", "High current gain pair (two NPNs)"),
    DARLINGTON_PNP("Darlington PNP", "PNP-DARL", "BJT", "High current gain pair (two PNPs)"),
    IGBT("Insulated-Gate Bipolar Transistor", "IGBT", "Hybrid", "High-voltage, high-current power switching"),
    PHOTO_TRANSISTOR("Phototransistor", "PHOTO", "Optical", "Light-sensitive transistor used for detection");

    private final String displayName;
    private final String symbol;
    private final String category;
    private final String description;

    TransistorType(String displayName, String symbol, String category, String description) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.category = category;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getEnglishDisplayValue() {
        return displayName + " (" + symbol + ")";
    }
}
