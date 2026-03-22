package general.mechanics.gui.component.screen;

/**
 * Represents an interface that defines whether an object blocks the rendering of slots.
 * <p>
 * Implementing classes should provide a mechanism to determine if slot rendering
 * should be ignored, which can be useful for UI components that overlap with, or obscure,
 * slot areas in a graphical user interface.
 */
public interface SlotRenderBlocker {
    boolean blocksSlotRendering();
}
