package general.mechanics.api.electrical.transformers;

import lombok.Getter;

@Getter
public enum TransformerType {

    STEP_UP("Step-Up Transformer", "STEP_UP", "Increases voltage, decreases current"),
    STEP_DOWN("Step-Down Transformer", "STEP_DOWN", "Decreases voltage, increases current"),
    ISOLATION("Isolation Transformer", "ISOLATION", "Galvanic isolation between circuits"),
    CENTER_TAPPED("Center-Tapped Transformer", "CENTER_TAPPED", "Dual-output with grounded center tap"),
    AUTOTRANSFORMER("Autotransformer", "AUTO", "Single winding with variable tap for voltage adjustment"),
    TOROIDAL("Toroidal Transformer", "TOROIDAL", "Efficient design with low magnetic leakage"),
    FLYBACK("Flyback Transformer", "FLYBACK", "Used in SMPS and CRT circuits for high voltage pulses"),
    AUDIO("Audio Transformer", "AUDIO", "Impedance matching and isolation in audio circuits"),
    PULSE("Pulse Transformer", "PULSE", "Transfers digital or timing signals"),
    CURRENT("Current Transformer", "CURRENT", "Measures current by proportional magnetic coupling");

    private final String displayName;
    private final String symbol;
    private final String description;

    TransformerType(String displayName, String symbol, String description) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.description = description;
    }

    public String getEnglishDisplayValue() {
        return displayName + " (" + symbol + ")";
    }
}

