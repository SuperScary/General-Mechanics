package general.mechanics.api.power;

import lombok.Getter;

public enum PowerPhase {

    DC("DC", "Direct Current"),
    SINGLE_PHASE("1φ", "Single Phase AC"),
    SPLIT_PHASE("1φ (split)", "Split Phase AC (North America 120/240V)"),
    TWO_PHASE("2φ", "Two Phase AC (90°)"),
    THREE_PHASE("3φ", "Three Phase AC (120°)"),
    SIX_PHASE("6φ", "Six Phase AC (HV Conversion)");

    @Getter
    private final String abbreviation;

    @Getter
    private final String displayName;

    @Getter
    private final String symbol = "φ";

    PowerPhase(String abbreviation, String displayName) {
        this.abbreviation = abbreviation;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName + " (" + abbreviation + ")";
    }

}
