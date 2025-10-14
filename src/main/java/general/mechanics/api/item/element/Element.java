package general.mechanics.api.item.element;

/**
 * Enum representing all known elements on the periodic table.
 * Each element contains atomic number, symbol, atomic weight, and tint color.
 * Tint colors are in ARGB format (0xAARRGGBB) and represent the real-world appearance of each element.
 */
public enum Element {
    // Period 1
    HYDROGEN(1, "H", 1.008f, 0xFFFFFFFF), // Colorless gas
    HELIUM(2, "He", 4.003f, 0xFFD9FFFF), // Light blue gas
    
    // Period 2
    LITHIUM(3, "Li", 6.941f, 0xFFE0E0E0), // Light silver metal
    BERYLLIUM(4, "Be", 9.012f, 0xFFB8B8B8), // Steel gray metal
    BORON(5, "B", 10.811f, 0xFF8B4513), // Dark brown/black
    CARBON(6, "C", 12.011f, 0xFF2F2F2F), // Black (graphite/coal)
    NITROGEN(7, "N", 14.007f, 0xFF87CEEB), // Colorless gas (light blue)
    OXYGEN(8, "O", 15.999f, 0xFF87CEEB), // Colorless gas (light blue)
    FLUORINE(9, "F", 18.998f, 0xFF90E050), // Pale yellow gas
    NEON(10, "Ne", 20.180f, 0xFFB3E3F5), // Colorless gas (light blue)
    
    // Period 3
    SODIUM(11, "Na", 22.990f, 0xFFD8D8D8), // Light silver metal
    MAGNESIUM(12, "Mg", 24.305f, 0xFFD0D0D0), // Silver-white metal
    ALUMINUM(13, "Al", 26.982f, 0xFFC8C8C8), // Silver metal
    SILICON(14, "Si", 28.085f, 0xFFA0A0A0), // Dark gray metalloid
    PHOSPHORUS(15, "P", 30.974f, 0xFFFF8000), // Red/orange (red phosphorus)
    SULFUR(16, "S", 32.065f, 0xFFFFFF00), // Bright yellow
    CHLORINE(17, "Cl", 35.453f, 0xFF90E050), // Yellow-green gas
    ARGON(18, "Ar", 39.948f, 0xFF87CEEB), // Colorless gas (light blue)
    
    // Period 4
    POTASSIUM(19, "K", 39.098f, 0xFFD4D4D4), // Light silver metal
    CALCIUM(20, "Ca", 40.078f, 0xFFDCDCDC), // Very light silver metal
    SCANDIUM(21, "Sc", 44.956f, 0xFFE8E8E8), // Very light silver metal
    TITANIUM(22, "Ti", 47.867f, 0xFFC4C4C4), // Silver metal
    VANADIUM(23, "V", 50.942f, 0xFF8C8C9C), // Blue-gray metal
    CHROMIUM(24, "Cr", 51.996f, 0xFFBCBCBC), // Silver metal
    MANGANESE(25, "Mn", 54.938f, 0xFFB4B4B4), // Darker silver metal
    IRON(26, "Fe", 55.845f, 0xFFB8B8B8), // Steel gray metal
    COBALT(27, "Co", 58.933f, 0xFF6B6B8C), // Blue-gray metal
    NICKEL(28, "Ni", 58.693f, 0xFFACACAC), // Dark silver metal
    COPPER(29, "Cu", 63.546f, 0xFFB87333), // Copper color
    ZINC(30, "Zn", 65.409f, 0xFFA8A8A8), // Gray metal
    GALLIUM(31, "Ga", 69.723f, 0xFFA4A4A4), // Gray metal
    GERMANIUM(32, "Ge", 72.640f, 0xFF9C9C9C), // Dark gray metalloid
    ARSENIC(33, "As", 74.922f, 0xFF848484), // Gray metalloid
    SELENIUM(34, "Se", 78.971f, 0xFF7C7C7C), // Dark gray metalloid
    BROMINE(35, "Br", 79.904f, 0xFF8B4513), // Red-brown liquid
    KRYPTON(36, "Kr", 83.798f, 0xFF87CEEB), // Colorless gas (light blue)
    
    // Period 5
    RUBIDIUM(37, "Rb", 85.468f, 0xFFD0D0D0), // Light silver metal
    STRONTIUM(38, "Sr", 87.620f, 0xFFD8D8D8), // Very light silver metal
    YTTRIUM(39, "Y", 88.906f, 0xFFB8B8B8), // Silver metal
    ZIRCONIUM(40, "Zr", 91.224f, 0xFFB4B4B4), // Silver metal
    NIOBIUM(41, "Nb", 92.906f, 0xFFB0B0B0), // Silver metal
    MOLYBDENUM(42, "Mo", 95.940f, 0xFFACACAC), // Silver metal
    TECHNETIUM(43, "Tc", 98.000f, 0xFFA8A8A8), // Silver metal
    RUTHENIUM(44, "Ru", 101.070f, 0xFFA4A4A4), // Silver metal
    RHODIUM(45, "Rh", 102.906f, 0xFFA0A0A0), // Silver metal
    PALLADIUM(46, "Pd", 106.420f, 0xFF9C9C9C), // Silver metal
    SILVER(47, "Ag", 107.868f, 0xFFE0E0E0), // Bright silver metal
    CADMIUM(48, "Cd", 112.411f, 0xFF989898), // Gray metal
    INDIUM(49, "In", 114.818f, 0xFF949494), // Gray metal
    TIN(50, "Sn", 118.710f, 0xFF909090), // Gray metal
    ANTIMONY(51, "Sb", 121.760f, 0xFF8C8C8C), // Gray metalloid
    TELLURIUM(52, "Te", 127.600f, 0xFF787878), // Dark gray metalloid
    IODINE(53, "I", 126.904f, 0xFF8B4513), // Dark gray/black solid
    XENON(54, "Xe", 131.293f, 0xFF87CEEB), // Colorless gas (light blue)
    
    // Period 6
    CESIUM(55, "Cs", 132.905f, 0xFFD4D4D4), // Light silver metal
    BARIUM(56, "Ba", 137.327f, 0xFFDCDCDC), // Very light silver metal
    LANTHANUM(57, "La", 138.905f, 0xFFB8B8B8), // Silver metal
    CERIUM(58, "Ce", 140.116f, 0xFFB4B4B4), // Silver metal
    PRASEODYMIUM(59, "Pr", 140.908f, 0xFFB0B0B0), // Silver metal
    NEODYMIUM(60, "Nd", 144.242f, 0xFFACACAC), // Silver metal
    PROMETHIUM(61, "Pm", 145.000f, 0xFFA8A8A8), // Silver metal
    SAMARIUM(62, "Sm", 150.360f, 0xFFA4A4A4), // Silver metal
    EUROPIUM(63, "Eu", 151.964f, 0xFFA0A0A0), // Silver metal
    GADOLINIUM(64, "Gd", 157.250f, 0xFF9C9C9C), // Silver metal
    TERBIUM(65, "Tb", 158.925f, 0xFF989898), // Silver metal
    DYSPROSIUM(66, "Dy", 162.500f, 0xFF949494), // Silver metal
    HOLMIUM(67, "Ho", 164.930f, 0xFF909090), // Silver metal
    ERBIUM(68, "Er", 167.259f, 0xFF8C8C8C), // Silver metal
    THULIUM(69, "Tm", 168.934f, 0xFF888888), // Silver metal
    YTTERBIUM(70, "Yb", 173.040f, 0xFF848484), // Silver metal
    LUTETIUM(71, "Lu", 174.967f, 0xFF808080), // Silver metal
    HAFNIUM(72, "Hf", 178.490f, 0xFF9C9C9C), // Dark silver metal
    TANTALUM(73, "Ta", 180.948f, 0xFF989898), // Dark silver metal
    TUNGSTEN(74, "W", 183.840f, 0xFF949494), // Dark silver metal
    RHENIUM(75, "Re", 186.207f, 0xFF909090), // Dark silver metal
    OSMIUM(76, "Os", 190.230f, 0xFF8C8C8C), // Dark silver metal
    IRIDIUM(77, "Ir", 192.217f, 0xFF888888), // Dark silver metal
    PLATINUM(78, "Pt", 195.084f, 0xFFE8E8E8), // Bright silver metal
    GOLD(79, "Au", 196.967f, 0xFFFFD700), // Gold color
    MERCURY(80, "Hg", 200.590f, 0xFF848484), // Dark silver metal (liquid)
    THALLIUM(81, "Tl", 204.383f, 0xFF808080), // Dark silver metal
    LEAD(82, "Pb", 207.200f, 0xFF4A4A6B), // Dark blue-gray metal
    BISMUTH(83, "Bi", 208.980f, 0xFF7C7C7C), // Dark silver metal
    POLONIUM(84, "Po", 209.000f, 0xFF6B6B6B), // Dark gray metal
    ASTATINE(85, "At", 210.000f, 0xFF6B6B6B), // Dark gray metalloid
    RADON(86, "Rn", 222.000f, 0xFF87CEEB), // Colorless gas (light blue)
    
    // Period 7
    FRANCIUM(87, "Fr", 223.000f, 0xFFD0D0D0), // Light silver metal
    RADIUM(88, "Ra", 226.000f, 0xFFD8D8D8), // Very light silver metal
    ACTINIUM(89, "Ac", 227.000f, 0xFF7C7C7C), // Dark silver metal
    THORIUM(90, "Th", 232.038f, 0xFF6B6B8C), // Dark blue-gray metal
    PROTACTINIUM(91, "Pa", 231.036f, 0xFF787878), // Dark silver metal
    URANIUM(92, "U", 238.029f, 0xFF6B6B6B), // Dark gray metal
    NEPTUNIUM(93, "Np", 237.000f, 0xFF747474), // Dark silver metal
    PLUTONIUM(94, "Pu", 244.000f, 0xFF707070), // Dark silver metal
    AMERICIUM(95, "Am", 243.000f, 0xFF6C6C6C), // Dark silver metal
    CURIUM(96, "Cm", 247.000f, 0xFF686868), // Dark silver metal
    BERKELIUM(97, "Bk", 247.000f, 0xFF646464), // Dark silver metal
    CALIFORNIUM(98, "Cf", 251.000f, 0xFF606060), // Dark silver metal
    EINSTEINIUM(99, "Es", 252.000f, 0xFF5C5C5C), // Dark silver metal
    FERMIUM(100, "Fm", 257.000f, 0xFF585858), // Dark silver metal
    MENDELEVIUM(101, "Md", 258.000f, 0xFF545454), // Dark silver metal
    NOBELIUM(102, "No", 259.000f, 0xFF505050), // Dark silver metal
    LAWRENCIUM(103, "Lr", 262.000f, 0xFF4C4C4C), // Dark silver metal
    RUTHERFORDIUM(104, "Rf", 261.000f, 0xFF484848), // Dark silver metal
    DUBNIUM(105, "Db", 262.000f, 0xFF444444), // Dark silver metal
    SEABORGIUM(106, "Sg", 266.000f, 0xFF404040), // Dark silver metal
    BOHRIUM(107, "Bh", 264.000f, 0xFF3C3C3C), // Dark silver metal
    HASSIUM(108, "Hs", 269.000f, 0xFF383838), // Dark silver metal
    MEITNERIUM(109, "Mt", 268.000f, 0xFF343434), // Dark silver metal
    DARMSTADTIUM(110, "Ds", 271.000f, 0xFF303030), // Dark silver metal
    ROENTGENIUM(111, "Rg", 272.000f, 0xFF2C2C2C), // Dark silver metal
    COPERNICIUM(112, "Cn", 285.000f, 0xFF282828), // Dark silver metal
    NIHONIUM(113, "Nh", 284.000f, 0xFF4A4A6B), // Dark blue-gray metal
    FLEROVIUM(114, "Fl", 289.000f, 0xFF242424), // Dark silver metal
    MOSCOVIUM(115, "Mc", 288.000f, 0xFF202020), // Dark silver metal
    LIVERMORIUM(116, "Lv", 293.000f, 0xFF1C1C1C), // Dark silver metal
    TENNESSINE(117, "Ts", 294.000f, 0xFF181818), // Dark gray metalloid
    OGANESSON(118, "Og", 294.000f, 0xFF87CEEB); // Colorless gas (light blue)
    
    private final int atomicNumber;
    private final String symbol;
    private final float atomicWeight;
    private final int tintColor;
    
    Element(int atomicNumber, String symbol, float atomicWeight, int tintColor) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.atomicWeight = atomicWeight;
        this.tintColor = tintColor;
    }
    
    /**
     * @return the atomic number of this element
     */
    public int getAtomicNumber() {
        return atomicNumber;
    }
    
    /**
     * @return the chemical symbol of this element
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * @return the atomic weight of this element
     */
    public float getAtomicWeight() {
        return atomicWeight;
    }
    
    /**
     * @return the tint color in ARGB format (0xAARRGGBB) for rendering this element
     */
    public int getTintColor() {
        return tintColor;
    }
    
    /**
     * Gets an element by its atomic number
     * @param atomicNumber the atomic number to search for
     * @return the element with the given atomic number, or null if not found
     */
    public static Element getByAtomicNumber(int atomicNumber) {
        for (Element element : values()) {
            if (element.getAtomicNumber() == atomicNumber) {
                return element;
            }
        }
        return null;
    }
    
    /**
     * Gets an element by its chemical symbol
     * @param symbol the chemical symbol to search for
     * @return the element with the given symbol, or null if not found
     */
    public static Element getBySymbol(String symbol) {
        for (Element element : values()) {
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
