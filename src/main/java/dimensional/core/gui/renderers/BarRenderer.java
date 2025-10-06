package dimensional.core.gui.renderers;

import net.minecraft.client.gui.GuiGraphics;

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

	public abstract void render (GuiGraphics guiGraphics);

	public int getXPos () {
		return xPos;
	}

	public int getYPos () {
		return yPos;
	}

	public int getWidth () {
		return width;
	}

	public int getHeight () {
		return height;
	}

	public enum Color {
		RED   (0xFF600B00),
		BRIGHT_RED (0xFFB51500),
		ORANGE (0xFFFF4500),
		GREEN (0xFF00FF00),
		BLUE  (0xFF0000FF),
		YELLOW(0xFFFFFF00),
		BLACK (0xFF000000),
		WHITE (0xFFFFFFFF);

		private final int argb;

		Color(int argb) {
			this.argb = argb;
		}

		/**
		 * @return the ARGB color as an int (0xAARRGGBB)
		 */
		public int getArgb() {
			return argb;
		}

		@Override
		public String toString() {
			return String.format("#%08X", argb);
		}
	}

}
