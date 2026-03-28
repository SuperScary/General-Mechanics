package general.mechanics.gui.renderers;

import lombok.Getter;
import net.minecraft.client.gui.GuiGraphicsExtractor;

@Getter
public abstract class BarRenderer {

	private final int xPos;
	private final int yPos;
	private final int width;
	private final int height;

	public BarRenderer(int xPos, int yPos, int width, int height) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}

	public abstract void render (GuiGraphicsExtractor guiGraphics);

    @Getter
    public enum Color {
		RED   (0xFF600B00),
		BRIGHT_RED (0xFFB51500),
		ORANGE (0xFFFF4500),
		GREEN (0xFF00FF00),
        BRIGHT_GREEN (0xFF00B500),
		BLUE  (0xFF0000FF),
		YELLOW(0xFFFFFF00),
		BLACK (0xFF000000),
		WHITE (0xFFFFFFFF);

        private final int argb;

		Color(int argb) {
			this.argb = argb;
		}

        @Override
		public String toString() {
			return String.format("#%08X", argb);
		}
	}

}
