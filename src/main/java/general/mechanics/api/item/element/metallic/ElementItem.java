package general.mechanics.api.item.element.metallic;

import general.mechanics.GM;
import general.mechanics.api.item.base.BaseItem;
import general.mechanics.api.item.element.ElementType;
import general.mechanics.api.tags.CoreTags;
import lombok.Getter;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElementItem extends BaseItem {

    @Getter
    private final ElementType element;
    private final ElementDustItem dust;
    private final ElementPlateItem plate;
    private final ElementNuggetItem nugget;
    private final ElementRawItem raw;
    private final ElementPileItem pile;
    private final ElementRodItem rod;
    @Getter
    private final Properties properties;

    public ElementItem(Properties properties, ElementType element) {
        super(properties);
        this.properties = properties;
        this.element = element;
        this.dust = new ElementDustItem(this);
        this.plate = new ElementPlateItem(this);
        this.nugget = new ElementNuggetItem(this);
        this.raw = new ElementRawItem(this);
        this.pile = new ElementPileItem(this);
        this.rod = new ElementRodItem(this);
    }

    public int getAtomicNumber() {
        return element.getAtomicNumber();
    }

    public String getAtomicSymbol() {
        return element.getSymbol();
    }

    public int getTint() {
        return element.getTintColor();
    }

    public ElementDustItem getDustItem() {
        return dust;
    }

    public ElementPlateItem getPlateItem() {
        return plate;
    }

    public ElementNuggetItem getNuggetItem() {
        return nugget;
    }

    public ElementRawItem getRawItem() {
        return raw;
    }

    public ElementPileItem getPileItem() {
        return pile;
    }

    public ElementRodItem getRodItem() {
        return rod;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(String.format("§e" + getAtomicSymbol())));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static int getColor(ItemStack stack, int index) {
        Item item = stack.getItem();

        if (item instanceof ElementItem element) {
            return element.getTint();
        }

        return -1;
    }

    public void getRecipes(RecipeOutput consumer, Criterion<InventoryChangeTrigger.TriggerInstance> has) {
        // Ingot -> Nugget
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getNuggetItem(), 9)
                .requires(this)
                .unlockedBy("has_element", has)
                .save(consumer, GM.getResource("elements/" + getRegistryName().getPath() + "_to_nugget"));

        // Hammer + this -> Dust
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getDustItem(), 1)
                .requires(CoreTags.Items.HAMMERS)
                .requires(this)
                .unlockedBy("has_element", has)
                .save(consumer, GM.getResource("elements/" + getRegistryName().getPath() + "_to_dust"));

        // Hammer + this + this -> Plate
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getPlateItem(), 1)
                .pattern("H")
                .pattern("I")
                .pattern("I")
                .define('H', CoreTags.Items.HAMMERS)
                .define('I', this)
                .unlockedBy("has_element", has)
                .save(consumer, GM.getResource("elements/" + getRegistryName().getPath() + "_to_plate"));

        // Dust -> Raw
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemStack(this::getDustItem, 3)), RecipeCategory.MISC, this::getRawItem, 0.6f, 200)
                .unlockedBy("has_element", has)
                .save(consumer, GM.getResource("elements/" + getRegistryName().getPath() + "_smelt_to_raw"));

        // Raw -> Ingot
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(this::getRawItem), RecipeCategory.MISC, this, 0.6f, 250)
                .unlockedBy("has_element", has)
                .save(consumer, GM.getResource("elements/" + getRegistryName().getPath() + "_smelt_to_ingot"));

        // Dust -> Pile
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this::getPileItem, 4)
                .requires(CoreTags.Items.HAMMERS)
                .requires(this::getDustItem)
                .unlockedBy("has_element", has)
                .save(consumer, GM.getResource("elements/" + getRegistryName().getPath() + "_dust_to_pile"));

        // File + this -> Rod
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this::getRodItem, 1)
                .requires(CoreTags.Items.FILES)
                .requires(this)
                .unlockedBy("has_element", has)
                .save(consumer, GM.getResource("elements/" + getRegistryName().getPath() + "_to_rod"));
    }

}
