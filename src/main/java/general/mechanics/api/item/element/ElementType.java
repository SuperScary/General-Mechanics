package general.mechanics.api.item.element;

import lombok.Getter;
import lombok.Setter;

/**
 * This represents the entire known periodic table of elements.
 */
@Getter
public enum ElementType {
    // Period 1
    HYDROGEN("Hydrogen", "H", 1L, 0L, -1, false, 1, 1.008f, 0xFFFFFFFF),
    HELIUM("Helium", "He", 2L, 2L, -1, false, 2, 4.003f, 0xFFD9FFFF),

    // Period 2
    LITHIUM("Lithium", "Li", 3L, 4L, -1, false, 3, 6.941f, 0xFFE0E0E0),
    BERYLLIUM("Beryllium", "Be", 4L, 5L, -1, false, 4, 9.012f, 0xFFB8B8B8),
    BORON("Boron", "B", 5L, 6L, -1, false, 5, 10.811f, 0xFF8B4513),
    CARBON("Carbon", "C", 6L, 6L, -1, false, 6, 12.011f, 0xFF2F2F2F),
    NITROGEN("Nitrogen", "N", 7L, 7L, -1, false, 7, 14.007f, 0xFF87CEEB),
    OXYGEN("Oxygen", "O", 8L, 8L, -1, false, 8, 15.999f, 0xFF87CEEB),
    FLUORINE("Fluorine", "F", 9L, 10L, -1, false, 9, 18.998f, 0xFF90E050),
    NEON("Neon", "Ne", 10L, 10L, -1, false, 10, 20.180f, 0xFFB3E3F5),

    // Period 3
    SODIUM("Sodium", "Na", 11L, 12L, -1, false, 11, 22.990f, 0xFFD8D8D8),
    MAGNESIUM("Magnesium", "Mg", 12L, 12L, -1, false, 12, 24.305f, 0xFFD0D0D0),
    ALUMINUM("Aluminum", "Al", 13L, 14L, -1, false, 13, 26.982f, 0xFFC8C8C8),
    SILICON("Silicon", "Si", 14L, 14L, -1, false, 14, 28.085f, 0xFFA0A0A0),
    PHOSPHORUS("Phosphorus", "P", 15L, 16L, -1, false, 15, 30.974f, 0xFFFF8000),
    SULFUR("Sulfur", "S", 16L, 16L, -1, false, 16, 32.065f, 0xFFFFFF00),
    CHLORINE("Chlorine", "Cl", 17L, 18L, -1, false, 17, 35.453f, 0xFF90E050),
    ARGON("Argon", "Ar", 18L, 22L, -1, false, 18, 39.948f, 0xFF87CEEB),

    // Period 4
    POTASSIUM("Potassium", "K", 19L, 20L, -1, false, 19, 39.098f, 0xFFD4D4D4),
    CALCIUM("Calcium", "Ca", 20L, 20L, -1, false, 20, 40.078f, 0xFFDCDCDC),
    SCANDIUM("Scandium", "Sc", 21L, 24L, -1, false, 21, 44.956f, 0xFFE8E8E8),
    TITANIUM("Titanium", "Ti", 22L, 26L, -1, false, 22, 47.867f, 0xFFC4C4C4),
    VANADIUM("Vanadium", "V", 23L, 28L, -1, false, 23, 50.942f, 0xFF8C8C9C),
    CHROMIUM("Chromium", "Cr", 24L, 28L, -1, false, 24, 51.996f, 0xFFBCBCBC),
    MANGANESE("Manganese", "Mn", 25L, 30L, -1, false, 25, 54.938f, 0xFFB4B4B4),
    IRON("Iron", "Fe", 26L, 30L, -1, false, 26, 55.845f, 0xFFB8B8B8),
    COBALT("Cobalt", "Co", 27L, 32L, -1, false, 27, 58.933f, 0xFF6B6B8C),
    NICKEL("Nickel", "Ni", 28L, 31L, -1, false, 28, 58.693f, 0xFFACACAC),
    COPPER("Copper", "Cu", 29L, 35L, -1, false, 29, 63.546f, 0xFFB87333),
    ZINC("Zinc", "Zn", 30L, 35L, -1, false, 30, 65.409f, 0xFFA8A8A8),
    GALLIUM("Gallium", "Ga", 31L, 39L, -1, false, 31, 69.723f, 0xFFA4A4A4),
    GERMANIUM("Germanium", "Ge", 32L, 41L, -1, false, 32, 72.640f, 0xFF9C9C9C),
    ARSENIC("Arsenic", "As", 33L, 42L, -1, false, 33, 74.922f, 0xFF848484),
    SELENIUM("Selenium", "Se", 34L, 45L, -1, false, 34, 78.971f, 0xFF7C7C7C),
    BROMINE("Bromine", "Br", 35L, 45L, -1, false, 35, 79.904f, 0xFF8B4513),
    KRYPTON("Krypton", "Kr", 36L, 48L, -1, false, 36, 83.798f, 0xFF87CEEB),

    // Period 5
    RUBIDIUM("Rubidium", "Rb", 37L, 48L, -1, false, 37, 85.468f, 0xFFD0D0D0),
    STRONTIUM("Strontium", "Sr", 38L, 50L, -1, false, 38, 87.620f, 0xFFD8D8D8),
    YTTRIUM("Yttrium", "Y", 39L, 50L, -1, false, 39, 88.906f, 0xFFB8B8B8),
    ZIRCONIUM("Zirconium", "Zr", 40L, 51L, -1, false, 40, 91.224f, 0xFFB4B4B4),
    NIOBIUM("Niobium", "Nb", 41L, 52L, -1, false, 41, 92.906f, 0xFFB0B0B0),
    MOLYBDENUM("Molybdenum", "Mo", 42L, 54L, -1, false, 42, 95.940f, 0xFFACACAC),
    TECHNETIUM("Technetium", "Tc", 43L, 55L, -1, false, 43, 98.000f, 0xFFA8A8A8),
    RUTHENIUM("Ruthenium", "Ru", 44L, 57L, -1, false, 44, 101.070f, 0xFFA4A4A4),
    RHODIUM("Rhodium", "Rh", 45L, 58L, -1, false, 45, 102.906f, 0xFFA0A0A0),
    PALLADIUM("Palladium", "Pd", 46L, 60L, -1, false, 46, 106.420f, 0xFF9C9C9C),
    SILVER("Silver", "Ag", 47L, 61L, -1, false, 47, 107.868f, 0xFFE0E0E0),
    CADMIUM("Cadmium", "Cd", 48L, 64L, -1, false, 48, 112.411f, 0xFF989898),
    INDIUM("Indium", "In", 49L, 66L, -1, false, 49, 114.818f, 0xFF949494),
    TIN("Tin", "Sn", 50L, 69L, -1, false, 50, 118.710f, 0xFF909090),
    ANTIMONY("Antimony", "Sb", 51L, 71L, -1, false, 51, 121.760f, 0xFF8C8C8C),
    TELLURIUM("Tellurium", "Te", 52L, 76L, -1, false, 52, 127.600f, 0xFF787878),
    IODINE("Iodine", "I", 53L, 74L, -1, false, 53, 126.904f, 0xFF8B4513),
    XENON("Xenon", "Xe", 54L, 77L, -1, false, 54, 131.293f, 0xFF87CEEB),

    // Period 6
    CESIUM("Cesium", "Cs", 55L, 78L, -1, false, 55, 132.905f, 0xFFD4D4D4),
    BARIUM("Barium", "Ba", 56L, 81L, -1, false, 56, 137.327f, 0xFFDCDCDC),
    LANTHANUM("Lanthanum", "La", 57L, 82L, -1, false, 57, 138.905f, 0xFFB8B8B8),
    CERIUM("Cerium", "Ce", 58L, 82L, -1, false, 58, 140.116f, 0xFFB4B4B4),
    PRASEODYMIUM("Praseodymium", "Pr", 59L, 82L, -1, false, 59, 140.908f, 0xFFB0B0B0),
    NEODYMIUM("Neodymium", "Nd", 60L, 84L, -1, false, 60, 144.242f, 0xFFACACAC),
    PROMETHIUM("Promethium", "Pm", 61L, 84L, -1, false, 61, 145.000f, 0xFFA8A8A8),
    SAMARIUM("Samarium", "Sm", 62L, 88L, -1, false, 62, 150.360f, 0xFFA4A4A4),
    EUROPIUM("Europium", "Eu", 63L, 89L, -1, false, 63, 151.964f, 0xFFA0A0A0),
    GADOLINIUM("Gadolinium", "Gd", 64L, 93L, -1, false, 64, 157.250f, 0xFF9C9C9C),
    TERBIUM("Terbium", "Tb", 65L, 94L, -1, false, 65, 158.925f, 0xFF989898),
    DYSPROSIUM("Dysprosium", "Dy", 66L, 96L, -1, false, 66, 162.500f, 0xFF949494),
    HOLMIUM("Holmium", "Ho", 67L, 98L, -1, false, 67, 164.930f, 0xFF909090),
    ERBIUM("Erbium", "Er", 68L, 99L, -1, false, 68, 167.259f, 0xFF8C8C8C),
    THULIUM("Thulium", "Tm", 69L, 100L, -1, false, 69, 168.934f, 0xFF888888),
    YTTERBIUM("Ytterbium", "Yb", 70L, 103L, -1, false, 70, 173.040f, 0xFF848484),
    LUTETIUM("Lutetium", "Lu", 71L, 104L, -1, false, 71, 174.967f, 0xFF808080),
    HAFNIUM("Hafnium", "Hf", 72L, 106L, -1, false, 72, 178.490f, 0xFF9C9C9C),
    TANTALUM("Tantalum", "Ta", 73L, 108L, -1, false, 73, 180.948f, 0xFF989898),
    TUNGSTEN("Tungsten", "W", 74L, 110L, -1, false, 74, 183.840f, 0xFF949494),
    RHENIUM("Rhenium", "Re", 75L, 111L, -1, false, 75, 186.207f, 0xFF909090),
    OSMIUM("Osmium", "Os", 76L, 114L, -1, false, 76, 190.230f, 0xFF8C8C8C),
    IRIDIUM("Iridium", "Ir", 77L, 115L, -1, false, 77, 192.217f, 0xFF888888),
    PLATINUM("Platinum", "Pt", 78L, 117L, -1, false, 78, 195.084f, 0xFFE8E8E8),
    GOLD("Gold", "Au", 79L, 118L, -1, false, 79, 196.967f, 0xFFFFD700),
    MERCURY("Mercury", "Hg", 80L, 121L, -1, false, 80, 200.590f, 0xFF848484),
    THALLIUM("Thallium", "Tl", 81L, 123L, -1, false, 81, 204.383f, 0xFF808080),
    LEAD("Lead", "Pb", 82L, 125L, -1, false, 82, 207.200f, 0xFF4A4A6B),
    BISMUTH("Bismuth", "Bi", 83L, 127L, -1, false, 83, 208.980f, 0xFF7C7C7C),
    POLONIUM("Polonium", "Po", 84L, 125L, -1, false, 84, 209.000f, 0xFF6B6B6B),
    ASTATINE("Astatine", "At", 85L, 125L, -1, false, 85, 210.000f, 0xFF6B6B6B),
    RADON("Radon", "Rn", 86L, 136L, -1, false, 86, 222.000f, 0xFF87CEEB),

    // Period 7
    FRANCIUM("Francium", "Fr", 87L, 136L, -1, false, 87, 223.000f, 0xFFD0D0D0),
    RADIUM("Radium", "Ra", 88L, 138L, -1, false, 88, 226.000f, 0xFFD8D8D8),
    ACTINIUM("Actinium", "Ac", 89L, 138L, -1, false, 89, 227.000f, 0xFF7C7C7C),
    THORIUM("Thorium", "Th", 90L, 142L, -1, false, 90, 232.038f, 0xFF6B6B8C),
    PROTACTINIUM("Protactinium", "Pa", 91L, 140L, -1, false, 91, 231.036f, 0xFF787878),
    URANIUM("Uranium", "U", 92L, 146L, -1, false, 92, 238.029f, 0xFF6B6B6B),
    NEPTUNIUM("Neptunium", "Np", 93L, 144L, -1, false, 93, 237.000f, 0xFF747474),
    PLUTONIUM("Plutonium", "Pu", 94L, 150L, -1, false, 94, 244.000f, 0xFF707070),
    AMERICIUM("Americium", "Am", 95L, 148L, -1, false, 95, 243.000f, 0xFF6C6C6C),
    CURIUM("Curium", "Cm", 96L, 151L, -1, false, 96, 247.000f, 0xFF686868),
    BERKELIUM("Berkelium", "Bk", 97L, 150L, -1, false, 97, 247.000f, 0xFF646464),
    CALIFORNIUM("Californium", "Cf", 98L, 153L, -1, false, 98, 251.000f, 0xFF606060),
    EINSTEINIUM("Einsteinium", "Es", 99L, 153L, -1, false, 99, 252.000f, 0xFF5C5C5C),
    FERMIUM("Fermium", "Fm", 100L, 157L, -1, false, 100, 257.000f, 0xFF585858),
    MENDELEVIUM("Mendelevium", "Md", 101L, 157L, -1, false, 101, 258.000f, 0xFF545454),
    NOBELIUM("Nobelium", "No", 102L, 157L, -1, false, 102, 259.000f, 0xFF505050),
    LAWRENCIUM("Lawrencium", "Lr", 103L, 159L, -1, false, 103, 262.000f, 0xFF4C4C4C),
    RUTHERFORDIUM("Rutherfordium", "Rf", 104L, 157L, -1, false, 104, 261.000f, 0xFF484848),
    DUBNIUM("Dubnium", "Db", 105L, 157L, -1, false, 105, 262.000f, 0xFF444444),
    SEABORGIUM("Seaborgium", "Sg", 106L, 160L, -1, false, 106, 266.000f, 0xFF404040),
    BOHRIUM("Bohrium", "Bh", 107L, 157L, -1, false, 107, 264.000f, 0xFF3C3C3C),
    HASSIUM("Hassium", "Hs", 108L, 161L, -1, false, 108, 269.000f, 0xFF383838),
    MEITNERIUM("Meitnerium", "Mt", 109L, 159L, -1, false, 109, 268.000f, 0xFF343434),
    DARMSTADTIUM("Darmstadtium", "Ds", 110L, 161L, -1, false, 110, 271.000f, 0xFF303030),
    ROENTGENIUM("Roentgenium", "Rg", 111L, 161L, -1, false, 111, 272.000f, 0xFF2C2C2C),
    COPERNICIUM("Copernicium", "Cn", 112L, 173L, -1, false, 112, 285.000f, 0xFF282828),
    NIHONIUM("Nihonium", "Nh", 113L, 171L, -1, false, 113, 284.000f, 0xFF4A4A6B),
    FLEROVIUM("Flerovium", "Fl", 114L, 175L, -1, false, 114, 289.000f, 0xFF242424),
    MOSCOVIUM("Moscovium", "Mc", 115L, 173L, -1, false, 115, 288.000f, 0xFF202020),
    LIVERMORIUM("Livermorium", "Lv", 116L, 177L, -1, false, 116, 293.000f, 0xFF1C1C1C),
    TENNESSINE("Tennessine", "Ts", 117L, 177L, -1, false, 117, 294.000f, 0xFF181818),
    OGANESSON("Oganesson", "Og", 118L, 176L, -1, false, 118, 294.000f, 0xFF87CEEB);

    @Getter
    @Setter
    private int atomicNumber;

    @Getter
    @Setter
    private String symbol;

    @Getter
    @Setter
    private float atomicWeight;

    @Getter
    @Setter
    private long protons;

    @Getter
    @Setter
    private long neutrons;

    @Getter
    @Setter
    private long halfLife;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean isotope;

    @Getter
    private final int tintColor;

    // Removed constant STABLE_HALF_LIFE; using -1 directly in enum constants

    ElementType(String name, String symbol, long protons, long neutrons, long halfLife, boolean isotope, int atomicNumber, float atomicWeight, int tintColor) {
        this.name = name;
        this.symbol = symbol;
        this.protons = protons;
        this.neutrons = neutrons;
        this.halfLife = halfLife;
        this.isotope = isotope;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
        this.tintColor = tintColor;
    }

    public long getMass() {
        return protons + neutrons;
    }

    /**
     * Gets an element by its atomic number
     *
     * @param atomicNumber the atomic number to search for
     * @return the element with the given atomic number, or null if not found
     */
    public static ElementType getByAtomicNumber(int atomicNumber) {
        for (ElementType element : values()) {
            if (element.getAtomicNumber() == atomicNumber) {
                return element;
            }
        }
        return null;
    }

    /**
     * Gets an element by its chemical symbol
     *
     * @param symbol the chemical symbol to search for
     * @return the element with the given symbol, or null if not found
     */
    public static ElementType getBySymbol(String symbol) {
        for (ElementType element : values()) {
            if (element.getSymbol().equals(symbol)) {
                return element;
            }
        }
        return null;
    }

    /**
     * @return the display name of this element (capitalized)
     */
    public String getDisplayName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
