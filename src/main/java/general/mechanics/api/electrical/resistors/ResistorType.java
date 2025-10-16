package general.mechanics.api.electrical.resistors;

public enum ResistorType {

    OHM_10(10f, "10Ω", "Used for current limiting and power applications"),
    OHM_100(100f, "100Ω", "Common for LED current limiting and small load resistors"),
    OHM_220(220f, "220Ω", "Standard LED resistor for 5V logic circuits"),
    OHM_330(330f, "330Ω", "Typical for logic-level current limiting or pull-downs"),
    OHM_470(470f, "470Ω", "Used in bias networks and general-purpose circuits"),
    OHM_1K(1_000f, "1kΩ", "Common pull-up or pull-down resistor value"),
    OHM_4_7K(4_700f, "4.7kΩ", "Typical for I²C pull-ups and biasing"),
    OHM_10K(10_000f, "10kΩ", "Most common resistor for general signal conditioning"),
    OHM_47K(47_000f, "47kΩ", "Used in analog filters, voltage dividers, and bias networks"),
    OHM_100K(100_000f, "100kΩ", "High-value resistor for voltage dividers and sensors");

    private final float value;
    private final String displayValue;
    private final String description;

    ResistorType(float value, String displayValue, String description) {
        this.value = value;
        this.displayValue = displayValue;
        this.description = description;
    }

    public float getValue() {
        return value;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getDescription() {
        return description;
    }

    public String getEnglishDisplayValue() {
        return displayValue.replace("Ω", " ohms");
    }
}

