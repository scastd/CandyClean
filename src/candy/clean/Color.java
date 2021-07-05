package candy.clean;

public class Color {
	private String colorStr;
	private BackgroundColor backgroundColor;

	public Color(int colorNumber) throws CandyCleanException {
		this.backgroundColor = BackgroundColor.values()[colorNumber];
		this.setColorWithBackground();
	}

	public Color(BackgroundColor backgroundColor) throws CandyCleanException {
		this.backgroundColor = backgroundColor;
		this.setColorWithBackground();
	}

	public String getColorStr() {
		return this.colorStr;
	}

	public void setColorStr(String colorStr) {
		this.colorStr = colorStr;
	}

	public BackgroundColor getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(BackgroundColor backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	private void setColorWithBackground() throws CandyCleanException {
		switch (this.backgroundColor) {
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

			default:
				throw new CandyCleanException("Incorrect color");
		}
	}

	@Override
	public String toString() {
		return this.colorStr;
	}

	public void setToBlank() {
		this.colorStr = Constants.BLACK;
		this.backgroundColor = BackgroundColor.BLACK;
	}

	public boolean isBlank() {
		return this.backgroundColor == BackgroundColor.BLACK;
	}
}
