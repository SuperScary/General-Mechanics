package general.mechanics.api.electrical.capacitors;

import lombok.Getter;

@Getter
public enum CapacitorType {

    PF_100(100e-12f, "100pF", "Used for high-frequency filtering and tuning circuits"),
    PF_220(220e-12f, "220pF", "Common for oscillator and RF applications"),
    PF_470(470e-12f, "470pF", "Used in timing, noise suppression, and RF filtering"),
    NF_1(1e-9f, "1nF", "Used in coupling, filtering, and snubber circuits"),
    NF_10(10e-9f, "10nF", "Common for timing and small signal filtering"),
    NF_100(100e-9f, "100nF", "Universal decoupling capacitor used across ICs and microcontrollers"),
    UF_1(1e-6f, "1µF", "Used for coupling, timing, and general smoothing"),
    UF_4_7(4.7e-6f, "4.7µF", "Intermediate filtering for audio and control circuits"),
    UF_10(10e-6f, "10µF", "Power supply smoothing and signal coupling applications"),
    UF_47(47e-6f, "47µF", "Used for bulk decoupling and energy storage"),
    UF_100(100e-6f, "100µF", "Power rail filtering and load stabilization"),
    UF_220(220e-6f, "220µF", "High-capacitance filtering for low-frequency circuits"),
    UF_470(470e-6f, "470µF", "Large electrolytic capacitor for DC power filtering and reservoirs");

    private final float value;          // value in farads
    private final String displayValue;  // display-friendly label
    private final String description;   // use-case description

    CapacitorType(float value, String displayValue, String description) {
        this.value = value;
        this.displayValue = displayValue;
        this.description = description;
    }

    public String getEnglishDisplayValue() {
        if (displayValue.endsWith("pF")) return displayValue.replace("pF", " picofarads");
        if (displayValue.endsWith("nF")) return displayValue.replace("nF", " nanofarads");
        if (displayValue.endsWith("µF")) return displayValue.replace("µF", " microfarads");
        return displayValue + " farads";
    }
}


