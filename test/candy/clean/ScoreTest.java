package candy.clean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {
	private Score score;
	private Score scoreObjective;

	@Before
	public void setUp() {
	    this.score = new Score();
	    this.scoreObjective = new Score(1000);
	}

	@Test
	public void testGetSetTotalScore() {
	    assertEquals(0, this.score.getCurrentScore());
	    assertEquals(0, this.scoreObjective.getCurrentScore());

	    this.score.setCurrentScore(10);
	    this.scoreObjective.setCurrentScore(500);

	    assertEquals(10, this.score.getCurrentScore());
	    assertEquals(500, this.scoreObjective.getCurrentScore());
	}

	@Test
	public void testGetSetStreak() {
		assertEquals(0, this.score.getStreak());
		assertEquals(0, this.scoreObjective.getStreak());

		this.score.setStreak(10);
		this.scoreObjective.setStreak(500);

		assertEquals(10, this.score.getStreak());
		assertEquals(500, this.scoreObjective.getStreak());
	}

	@Test
	public void testGetSetMultiplier() {
		assertEquals(1, this.score.getMultiplier());
		assertEquals(1, this.scoreObjective.getMultiplier());

		this.score.setMultiplier(10);
		this.scoreObjective.setMultiplier(500);

		assertEquals(10, this.score.getMultiplier());
		assertEquals(500, this.scoreObjective.getMultiplier());
	}

	@Test
	public void testGetSetObjective() {
		assertEquals(300, this.score.getObjective());
		assertEquals(1000, this.scoreObjective.getObjective());

		this.score.setObjective(10);
		this.scoreObjective.setObjective(500);

		assertEquals(10, this.score.getObjective());
		assertEquals(500, this.scoreObjective.getObjective());
	}

	@Test
	public void testObjectiveReached() {
	    assertFalse(this.score.objectiveReached());
	    assertFalse(this.scoreObjective.objectiveReached());

	    this.score.setCurrentScore(400);
	    this.scoreObjective.setCurrentScore(999);

	    assertTrue(this.score.objectiveReached());
	    assertFalse(this.scoreObjective.objectiveReached());
	}

	@Test
	public void testIncrementScore() {
	    assertEquals(0, this.score.getCurrentScore());
	    assertEquals(0, this.score.getStreak());
	    assertEquals(1, this.score.getMultiplier());

	    this.score.incrementScore();

		assertEquals(100, this.score.getCurrentScore());
		assertEquals(1, this.score.getStreak());
		assertEquals(1, this.score.getMultiplier());

		for (int i = 0; i < 4; i++) {
			this.score.incrementScore();
		}

		assertEquals(500, this.score.getCurrentScore());
		assertEquals(5, this.score.getStreak());
		assertEquals(2, this.score.getMultiplier());

		for (int i = 0; i < 10; i++) {
			this.score.incrementScore();
		}

		assertEquals(3000, this.score.getCurrentScore());
		assertEquals(15, this.score.getStreak());
		assertEquals(7, this.score.getMultiplier());
	}

	@Test
	public void testResetScore() {
		assertEquals(0, this.score.getStreak());
		assertEquals(1, this.score.getMultiplier());

		this.score.incrementScore();
		this.score.incrementScore();
		this.score.incrementScore();
		this.score.incrementScore();

		assertEquals(4, this.score.getStreak());
		assertEquals(1, this.score.getMultiplier());

		this.score.resetScore();

		assertEquals(0, this.score.getStreak());
		assertEquals(1, this.score.getMultiplier());
	}

	@Test
	public void testToString() {
		this.score.setObjective(3000);

	    assertEquals("Score: 0/3000 | Multiplier: x1 | Streak: 0", this.score.toString());

		for (int i = 0; i < 10; i++) {
			this.score.incrementScore();
		}

		assertEquals("Score: 1500/3000 | Multiplier: x3 | Streak: 10", this.score.toString());
	}
}
