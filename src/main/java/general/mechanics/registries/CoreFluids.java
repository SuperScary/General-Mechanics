package general.mechanics.registries;

import general.mechanics.GM;
import general.mechanics.api.fluid.BaseFluid;
import general.mechanics.api.fluid.FluidDefinition;
import general.mechanics.api.item.ItemDefinition;
import general.mechanics.api.block.BlockDefinition;
import general.mechanics.api.formula.Formula;
import general.mechanics.tab.CoreTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class CoreFluids {

    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, GM.MODID);

    private static final List<FluidDefinition> FLUIDS = new ArrayList<>();

    // ──────────────────────────────
    // Fuels and Petroleum Derivatives
    // ──────────────────────────────
    public static final FluidDefinition CRUDE_OIL = registerFluid("Crude Oil", 0xFF281E15, new Vector3f(0.0F, 0.0F, 0.0F), "C₁₂H₂₃");
    public static final FluidDefinition REFINED_OIL = registerFluid("Refined Oil", 0xFF3B2714, new Vector3f(0.05F, 0.03F, 0.02F), "C₁₀H₂₂");
    public static final FluidDefinition GASOLINE = registerFluid("Gasoline", 0xFFFF9933, new Vector3f(0.8F, 0.6F, 0.3F), "C₈H₁₈");
    public static final FluidDefinition DIESEL = registerFluid("Diesel", 0xFFFFCC00, new Vector3f(0.5F, 0.4F, 0.1F), "C₁₂H₂₃");
    public static final FluidDefinition KEROSENE = registerFluid("Kerosene", 0xFFFFD580, new Vector3f(0.9F, 0.8F, 0.5F), "C₁₂H₂₆");
    public static final FluidDefinition NAPHTHA = registerFluid("Naphtha", 0xFFE8C075, new Vector3f(0.7F, 0.6F, 0.3F), "C₇H₁₆");

    // ──────────────────────────────
    // Alcohols and Solvents
    // ──────────────────────────────
    public static final FluidDefinition METHANOL = registerFluid("Methanol", 0xFFBBE6FF, new Vector3f(0.8F, 0.9F, 1.0F), "CH₃OH", false, true, 298L);
    public static final FluidDefinition ETHANOL = registerFluid("Ethanol", 0xFFCCEFFF, new Vector3f(0.8F, 0.9F, 1.0F), "C₂H₅OH", false, true, 298L);
    public static final FluidDefinition ACETONE = registerFluid("Acetone", 0xFFF0F0F0, new Vector3f(0.9F, 0.9F, 0.9F), "C₃H₆O", false, true, 298L);
    public static final FluidDefinition GLYCEROL = registerFluid("Glycerol", 0xFFE8E8FF, new Vector3f(0.9F, 0.9F, 1.0F), "C₃H₈O₃", false, true, 298L);

    // ──────────────────────────────
    // Aromatics and Hydrocarbon Feedstocks
    // ──────────────────────────────
    public static final FluidDefinition BENZENE = registerFluid("Benzene", 0xFFCC9966, new Vector3f(0.85F, 0.7F, 0.4F), "C₆H₆");
    public static final FluidDefinition TOLUENE = registerFluid("Toluene", 0xFFB88A5A, new Vector3f(0.8F, 0.6F, 0.35F), "C₇H₈");
    public static final FluidDefinition XYLENE = registerFluid("Xylene", 0xFFB97A56, new Vector3f(0.75F, 0.55F, 0.35F), "C₈H₁₀");
    public static final FluidDefinition ETHYLENE = registerFluid("Ethylene", 0xFFF5E6CC, new Vector3f(0.9F, 0.9F, 0.8F), "C₂H₄", false, false, 273L);
    public static final FluidDefinition PROPYLENE = registerFluid("Propylene", 0xFFEEDDBB, new Vector3f(0.9F, 0.85F, 0.75F), "C₃H₆", false, false, 273L);
    public static final FluidDefinition BUTADIENE = registerFluid("Butadiene", 0xFFE6CCAA, new Vector3f(0.85F, 0.75F, 0.6F), "C₄H₆", false, false, 273L);
    public static final FluidDefinition STYRENE = registerFluid("Styrene", 0xFFD9B38C, new Vector3f(0.85F, 0.7F, 0.5F), "C₈H₈");

    // ──────────────────────────────
    // Acids and Bases
    // ──────────────────────────────
    public static final FluidDefinition SULFURIC_ACID = registerFluid("Sulfuric Acid", 0xFFFFFF66, new Vector3f(0.9F, 0.8F, 0.4F), "H₂SO₄", true, false, 298L);
    public static final FluidDefinition HYDROCHLORIC_ACID = registerFluid("Hydrochloric Acid", 0xFF99FFFF, new Vector3f(0.6F, 0.9F, 1.0F), "HCl", true, false, 298L);
    public static final FluidDefinition NITRIC_ACID = registerFluid("Nitric Acid", 0xFFFFE066, new Vector3f(0.9F, 0.8F, 0.4F), "HNO₃", true, false, 298L);
    public static final FluidDefinition AMMONIA = registerFluid("Ammonia", 0xFF99CCFF, new Vector3f(0.6F, 0.7F, 1.0F), "NH₃", false, true, 298L);
    public static final FluidDefinition LIQUID_AMMONIA = registerFluid("Liquid Ammonia", 0xFF99CCFF, new Vector3f(0.6F, 0.7F, 1.0F), "NH₃(l)", false, true, 240L);

    // ──────────────────────────────
    // Elements and Gases
    // ──────────────────────────────
    public static final FluidDefinition HYDROGEN = registerFluid("Hydrogen", 0xFF99FFFF, new Vector3f(0.6F, 0.9F, 1.0F), "H₂", false, false, 20L);
    public static final FluidDefinition OXYGEN = registerFluid("Oxygen", 0xFF66CCFF, new Vector3f(0.4F, 0.7F, 1.0F), "O₂", false, false, 90L);
    public static final FluidDefinition NITROGEN = registerFluid("Nitrogen", 0xFFCCCCFF, new Vector3f(0.8F, 0.8F, 1.0F), "N₂", false, false, 77L);
    public static final FluidDefinition CARBON_MONOXIDE = registerFluid("Carbon Monoxide", 0xFF999999, new Vector3f(0.5F, 0.5F, 0.5F), "CO", false, false, 82L);
    public static final FluidDefinition CARBON_DIOXIDE = registerFluid("Carbon Dioxide", 0xFFAAAAAA, new Vector3f(0.6F, 0.6F, 0.6F), "CO₂", false, false, 195L);

    // ──────────────────────────────
    // Water Variants & Cryogenic Liquids
    // ──────────────────────────────
    public static final FluidDefinition DISTILLED_WATER = registerFluid("Distilled Water", 0xFF66B3FF, new Vector3f(0.4F, 0.6F, 1.0F), "H₂O");
    public static final FluidDefinition HEAVY_WATER = registerFluid("Heavy Water", 0xFF99B3FF, new Vector3f(0.5F, 0.6F, 1.0F), "D₂O");
    public static final FluidDefinition STEAM = registerFluid("Steam", 0xFFCCCCCC, new Vector3f(0.9F, 0.9F, 0.9F), "H₂O(g)", false, false, 373L);
    public static final FluidDefinition LIQUID_NITROGEN = registerFluid("Liquid Nitrogen", 0xFF99CCFF, new Vector3f(0.5F, 0.7F, 1.0F), "N₂(l)", false, false, 77L);
    public static final FluidDefinition LIQUID_OXYGEN = registerFluid("Liquid Oxygen", 0xFF6699FF, new Vector3f(0.3F, 0.5F, 1.0F), "O₂(l)", false, false, 90L);

    public static List<FluidDefinition> getFluids() {
        return Collections.unmodifiableList(FLUIDS);
    }

    public static BaseFluid getBaseFluid(FluidDefinition definition) {
        var type = definition.getType().get();
        if (type instanceof BaseFluid baseFluid) {
            return baseFluid;
        }
        return null;
    }

    public static FluidDefinition registerFluid(String englishName, int tintColor, Vector3f fogColor, String chemicalFormula) {
        return registerFluid(englishName, tintColor, fogColor, chemicalFormula, false, false, 298L); // room temperature in Kelvin
    }

    public static FluidDefinition registerFluid(String englishName, int tintColor, Vector3f fogColor, String chemicalFormula, boolean isAcidic, boolean isBasic, long temp) {
        String baseName = englishName.toLowerCase(Locale.ROOT).replace(' ', '_');

        // FluidType
        Supplier<FluidType> type = CoreFluidTypes.register(baseName, new BaseFluid(FluidType.Properties.create(), CoreFluidTypes.WATER_STILL, CoreFluidTypes.WATER_FLOWING, CoreFluidTypes.WATER_OVERLAY, tintColor, fogColor, isAcidic, isBasic, temp));

        // Source/Flowing with forward references
        final AtomicReference<Supplier<FlowingFluid>> sourceRef = new AtomicReference<>();
        final AtomicReference<Supplier<FlowingFluid>> flowingRef = new AtomicReference<>();

        BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(type,
                () -> sourceRef.get().get(),
                () -> flowingRef.get().get());

        Supplier<FlowingFluid> SOURCE = REGISTRY.register("source_" + baseName, () -> new BaseFlowingFluid.Source(properties));
        Supplier<FlowingFluid> FLOWING = REGISTRY.register("flowing_" + baseName, () -> new BaseFlowingFluid.Flowing(properties));
        sourceRef.set(SOURCE);
        flowingRef.set(FLOWING);

        // Block
        BlockDefinition<LiquidBlock> BLOCK = CoreBlocks.register(englishName, GM.getResource(baseName + "_block"),
                () -> new LiquidBlock(SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()), null, false, CoreTab.FLUIDS);

        // Bucket
        ItemDefinition<BucketItem> BUCKET = CoreItems.register(englishName + " Bucket", GM.getResource("bucket_" + baseName),
                (Item.Properties p) -> new BucketItem(SOURCE.get(), p.stacksTo(1).craftRemainder(Items.BUCKET)), CoreTab.FLUIDS);

        properties.block(BLOCK::block).bucket(BUCKET);

        if (chemicalFormula != null && !chemicalFormula.isBlank()) {
            String bucketKey = "item." + GM.MODID + "." + BUCKET.id().getPath();
            String blockItemKey = "block." + GM.MODID + "." + BLOCK.id().getPath();
            CoreFormulas.register(bucketKey, new Formula(BUCKET, chemicalFormula));
            CoreFormulas.register(blockItemKey, new Formula(BLOCK.item(), chemicalFormula));
        }

        FluidDefinition definition = new FluidDefinition(englishName, type, SOURCE, FLOWING, BLOCK, BUCKET);
        FLUIDS.add(definition);
        return definition;
    }

}
