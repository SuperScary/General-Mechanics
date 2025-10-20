package general.mechanics.api.electrical.ics;

import lombok.Getter;

@Getter
public enum IntegratedCircuitType {

    // --- Logic ICs ---
    IC_7400("7400 NAND Gate", "Logic", "Quad 2-input NAND gate"),
    IC_7402("7402 NOR Gate", "Logic", "Quad 2-input NOR gate"),
    IC_7404("7404 Inverter", "Logic", "Hex NOT gate"),
    IC_7408("7408 AND Gate", "Logic", "Quad 2-input AND gate"),
    IC_7432("7432 OR Gate", "Logic", "Quad 2-input OR gate"),
    IC_7486("7486 XOR Gate", "Logic", "Quad 2-input XOR gate"),
    IC_7474("7474 D Flip-Flop", "Logic", "Dual D-type flip-flop"),
    IC_74138("74138 Decoder", "Logic", "3-to-8 line decoder for address selection"),
    IC_7447("7447 BCD Decoder", "Logic", "BCD to 7-segment display driver"),
    IC_4017("4017 Counter", "Logic", "Decade counter and sequencer"),

    // --- Timer / Oscillator ICs ---
    IC_555("555 Timer", "Timer", "General-purpose timer, pulse generator, and oscillator"),
    IC_556("556 Dual Timer", "Timer", "Two 555 timers in one package"),
    IC_4040("4040 Counter", "Timer", "12-stage binary ripple counter"),
    IC_4093("4093 Schmitt Trigger", "Timer", "Quad NAND Schmitt trigger for clean signal edges"),

    // --- Analog / Signal ICs ---
    IC_741_OPAMP("741 Op-Amp", "Analog", "General-purpose operational amplifier"),
    IC_LM324("LM324 Quad Op-Amp", "Analog", "Four op-amps in a single package"),
    IC_LM358("LM358 Dual Op-Amp", "Analog", "Dual general-purpose op-amp"),
    IC_LM339("LM339 Comparator", "Analog", "Quad voltage comparator"),
    IC_LM393("LM393 Comparator", "Analog", "Dual voltage comparator"),
    IC_LM386("LM386 Audio Amplifier", "Analog", "Low-voltage audio amplifier"),

    // --- Power / Motor Control ICs ---
    IC_7805("LM7805 Regulator", "Power", "Fixed 5V voltage regulator"),
    IC_7812("LM7812 Regulator", "Power", "Fixed 12V voltage regulator"),
    IC_LM317("LM317 Regulator", "Power", "Adjustable voltage regulator"),
    IC_L293D("L293D Motor Driver", "Power", "Dual H-bridge motor driver for DC motors"),
    IC_ULN2003("ULN2003 Driver Array", "Power", "Seven Darlington transistor array for relay/solenoid control"),

    // --- Interface / Sensor ICs ---
    IC_MAX232("MAX232 Level Shifter", "Interface", "RS-232 serial communication voltage converter"),
    IC_LM75("LM75 Temperature Sensor", "Interface", "I²C-compatible digital temperature sensor"),
    IC_NE567("NE567 Tone Decoder", "Interface", "Frequency-to-voltage tone decoder"),
    IC_24C02("24C02 EEPROM", "Interface", "I²C serial EEPROM memory chip"),

    // --- Microcontroller / Processor ICs ---
    IC_ATMEGA328P("ATmega328P Microcontroller", "Microcontroller", "Programmable 8-bit controller");

    private final String displayName;
    private final String category;
    private final String description;

    IntegratedCircuitType(String displayName, String category, String description) {
        this.displayName = displayName;
        this.category = category;
        this.description = description;
    }

    public String getEnglishDisplayValue() {
        return displayName + " (" + category + ")";
    }
}

