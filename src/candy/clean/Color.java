package candy.clean;

public class Color {
	private String colorStr;

	public Color(BackgroundColor backgroundColor) {
		switch (backgroundColor) {
			case RED:
				this.colorStr = Constants.RED;
				break;

			case GREEN:
				this.colorStr = Constants.GREEN;
				break;

			case YELLOW:
				this.colorStr = Constants.YELLOW;
				break;

			case BLUE:
				this.colorStr = Constants.BLUE;
				break;

			case PURPLE:
				this.colorStr = Constants.PURPLE;
				break;

			case CYAN:
				this.colorStr = Constants.CYAN;
				break;

			case WHITE:
				this.colorStr = Constants.WHITE;
				break;

			case BLACK:
			default:
				this.colorStr = Constants.BLACK;
				break;
		}
	}

	public Color(int colorNumber) {
		this(BackgroundColor.values()[colorNumber]);
	}

	public String getColorStr() {
		return this.colorStr;
	}

	public void setColorStr(String colorStr) {
		this.colorStr = colorStr;
	}

	public void setToBlank() {
		this.colorStr = Constants.BLACK;
	}

	@Override
	public String toString() {
		return this.toString("  ");
	}

	public String toString(String str) {
		return this.colorStr + str + Constants.RESET;
	}
}
