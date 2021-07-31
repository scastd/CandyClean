package candy.clean;

public class Score {
	private int currentScore;
	private int streak;
	private int multiplier;
	private int objective;

	public Score() {
		this(300);
	}

	public Score(int objective) {
		this.currentScore = 0;
		this.streak = 0;
		this.multiplier = 1;
		this.objective = objective;
	}

	public int getCurrentScore() {
		return this.currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
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

	public int getObjective() {
		return this.objective;
	}

	public void setObjective(int objective) {
		this.objective = objective;
	}

	public boolean objectiveReached() {
		return this.currentScore >= this.objective;
	}

	public void incrementScore() {
		this.currentScore += Constants.BASE_SCORE * this.multiplier;
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
		return "Score: " + this.currentScore + "/" + this.objective + " | Multiplier: x" + this.multiplier + " | Streak: " + this.streak;
	}
}
