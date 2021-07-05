package candy.clean;

public class Score {
	private int totalScore;
	private int streak;
	private int multiplier;

	// Todo: añadir objetivo
	public Score() {
		this.totalScore = 0;
		this.streak = 0;
		this.multiplier = 1;
	}

	public int getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getStreak() {
		return this.streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public int getMultiplier() {
		return this.multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public void incrementScore() {
		this.totalScore += Constants.BASE_SCORE * this.multiplier;
		this.streak++;

		if (this.streak % 5 == 0)
			this.multiplier += 1;
		if (this.streak % 15 == 0)
			this.multiplier += 3;
	}

	public void resetScore() {
		this.multiplier = 1;
		this.streak = 0;
	}

	@Override
	public String toString() {
		return "Score: " + this.totalScore + " | Multiplier: x" + this.multiplier + " | Streak: " + this.streak;
	}
}
