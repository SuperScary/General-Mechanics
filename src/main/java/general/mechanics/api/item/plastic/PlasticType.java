package general.mechanics.api.item.plastic;

import net.minecraft.world.item.DyeColor;

/**
 * Enum representing different types of plastic materials.
 * Each type can be colored with any of the 16 dye colors.
 */
public enum PlasticType {
    POLYETHYLENE("Polyethylene", "PE", "C₂H₄"),
    POLYPROPYLENE("Polypropylene", "PP", "C₃H₆"),
    POLYSTYRENE("Polystyrene", "PS", "C₈H₈"),
    POLYVINYL_CHLORIDE("Polyvinyl Chloride", "PVC", "C₂H₃Cl"),
    POLYETHYLENE_TEREPHTHALATE("Polyethylene Terephthalate", "PET", "C₁₀H₈O₄"),
    ACRYLONITRILE_BUTADIENE_STYRENE("Acrylonitrile Butadiene Styrene", "ABS", "C₈H₈·C₄H₆·C₃H₃N"),
    POLYCARBONATE("Polycarbonate", "PC", "C₁₆H₁₄O₃"),
    NYLON("Nylon", "PA", "C₆H₁₁NO"),
    POLYURETHANE("Polyurethane", "PU", "C₃H₈N₂O"),
    POLYTETRAFLUOROETHYLENE("Polytetrafluoroethylene", "PTFE", "C₂F₄"),
    POLYETHERETHERKETONE("Polyetheretherketone", "PEEK", "C₁₉H₁₂O₃");

    private final String displayName;
    private final String abbreviation;
    private final String formula;

    PlasticType(String displayName, String abbreviation, String formula) {
        this.displayName = displayName;
        this.abbreviation = abbreviation;
        this.formula = formula;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getFormula() {
        return formula;
    }

    /**
     * Get all 16 dye colors for plastic coloring
     */
    public static DyeColor[] getAllColors() {
        return DyeColor.values();
    }
}
