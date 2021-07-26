package candy.clean;

public class Candy {
	private Color color;
	private char letter;
	private int special = 0;

	public Candy(char letter) {
		this.setLetterAndColor(letter);
	}

	public Candy(int num) {
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

	public void setLetterAndColor(char letter) {
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
				this.color = new Color(BackgroundColor.BLACK);
		}
	}

	public int isSpecial() {
		return this.special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public void setToBlank() {
		this.setLetterAndColor('E');
	}

	public boolean isBlank() {
		return this.letter == 'E';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o instanceof Candy) return this.letter == ((Candy) o).letter;

		return false;
	}

	@Override
	public String toString() {
		return this.toString(Character.toString(this.letter));
	}

	public String toString(String str) {
		return this.color.toString(str);
	}
}
