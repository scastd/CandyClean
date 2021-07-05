package candy.clean;

public class Candy {
	private Color color;
	private char letter;

	public Candy(char letter) throws CandyCleanException {
		this.setLetterAndColor(letter);
	}

	public Candy(int num) throws CandyCleanException {
		char[] letters = {'R', 'G', 'Y', 'B', 'P', 'C', 'W'};
		this.setLetterAndColor(letters[num]);
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public char getLetter() {
		return this.letter;
	}

	public void setLetterAndColor(char letter) throws CandyCleanException {
		this.letter = letter;

		switch (letter) {
			case 'R':
				this.color = new Color(BackgroundColor.RED);
				break;

			case 'G':
				this.color = new Color(BackgroundColor.GREEN);
				break;

			case 'Y':
				this.color = new Color(BackgroundColor.YELLOW);
				break;

			case 'B':
				this.color = new Color(BackgroundColor.BLUE);
				break;

			case 'P':
				this.color = new Color(BackgroundColor.PURPLE);
				break;

			case 'C':
				this.color = new Color(BackgroundColor.CYAN);
				break;

			case 'W':
				this.color = new Color(BackgroundColor.WHITE);
				break;

			default:
				throw new CandyCleanException("Incorrect color");
		}
	}

	public String toString(String str) {
		return this.color.toString() + str + Constants.RESET;
	}

	public void setToBlank() {
		this.color.setToBlank();
		this.letter = 'X';
	}

	@Override
	public String toString() {
		return this.color.toString() + this.letter + Constants.RESET;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o instanceof Candy) return this.letter == ((Candy) o).letter;

		return false;
	}

	public boolean isBlank() {
		return this.color.isBlank();
	}
}