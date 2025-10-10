package fluxmachines.core.datagen.language;

import fluxmachines.core.FluxMachines;
import fluxmachines.core.api.upgrade.UpgradeBase;
import fluxmachines.core.api.util.IDataProvider;
import fluxmachines.core.registries.CoreBlocks;
import fluxmachines.core.registries.CoreItems;
import fluxmachines.core.registries.CoreUpgrades;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CoreEnLangProvider extends LanguageProvider implements IDataProvider {

    public CoreEnLangProvider(DataGenerator generator) {
        super(generator.getPackOutput(), FluxMachines.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addManualStrings();
        addSubtitles();

        // ITEMS
        for (var item : CoreItems.getItems()) {
            add(item.asItem(), item.getEnglishName());
        }

        // BLOCKS
        for (var block : CoreBlocks.getBlocks()) {
            add(block.block(), block.getEnglishName());
        }

        // UPGRADES
        for (var upgrade : CoreUpgrades.getUpgrades()) {
            add(upgrade.get(), upgrade.getEnglishName());
        }

        // Upgrade descriptions
        for (var upgrade : CoreUpgrades.getUpgrades()) {
            UpgradeBase base = (UpgradeBase) upgrade.get();
            add(base.getDescriptionId() + ".desc", base.getEnglishDescription());
        }
    }

    protected void addManualStrings() {
        add("itemGroup." + FluxMachines.MODID, FluxMachines.NAME);
        add("gui.fluxmachines.progress", "Progress: "); // the space is important!!!!
        add("gui.fluxmachines.idle", "Idle");
        add("gui.fluxmachines.gui.settings.right", "Right Click to Expand");
        add("gui.fluxmachines.gui.settings.left", "Left Click to Expand");
        add("gui.fluxmachines.gui.settings", "Settings");
        add("gui.fluxmachines.itemlist", "Slot %s: %sx %s");
        add("gui.fluxmachines.press_shift", "Hold §e[SHIFT]§r for more info.");
        add("gui.fluxmachines.upgrade_tooltip", "Compatible Upgrades");
        add("gui.fluxmachines.upgrade_tooltip.item", "§7§o- §7§o%s §7§ox%s");
        add("gui.fluxmachines.gui.redstone_mode", "Redstone Mode");
        add("gui.fluxmachines.gui.auto_export", "Auto Export");
        add("gui.fluxmachines.gui.auto_import", "Auto Import");
        add("gui.fluxmachines.gui.enabled", "Enabled");
        add("gui.fluxmachines.gui.side_config", "Side Config");
        add("gui.fluxmachines.gui.status", "Status: %s");
        add("gui.fluxmachines.gui.status.active", "Active");
        add("gui.fluxmachines.gui.status.inactive", "Inactive");
        add("gui.fluxmachines.gui.status.error", "Error");
    }

    protected void addSubtitles() {
        add("subtitles.fluxmachines.plastic_block_place", "Block Placed");
        add("subtitles.fluxmachines.plastic_block_break", "Block Break");
    }
}
