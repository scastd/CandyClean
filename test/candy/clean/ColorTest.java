package candy.clean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {
	private Color black;
	private Color red;
	private Color green;
	private Color yellow;
	private Color blue;
	private Color purple;
	private Color cyan;
	private Color white;

	@Before
	public void setUp() {
		this.black = new Color(0);
		this.red = new Color(1);
		this.green = new Color(2);
		this.yellow = new Color(3);
		this.blue = new Color(4);
		this.purple = new Color(5);
		this.cyan = new Color(6);
		this.white = new Color(7);
	}

	@Test
	public void testGetColorStr() {
	    assertEquals(Constants.BLACK, this.black.getColorStr());
	    assertEquals(Constants.RED, this.red.getColorStr());
	    assertEquals(Constants.GREEN, this.green.getColorStr());
	    assertEquals(Constants.YELLOW, this.yellow.getColorStr());
	    assertEquals(Constants.BLUE, this.blue.getColorStr());
	    assertEquals(Constants.PURPLE, this.purple.getColorStr());
	    assertEquals(Constants.CYAN, this.cyan.getColorStr());
	    assertEquals(Constants.WHITE, this.white.getColorStr());
	}

	@Test
	public void testSetColorStr() {
	    this.black.setColorStr(Constants.CYAN);
	    assertEquals(Constants.CYAN, this.black.getColorStr());
	}

	@Test
	public void testSetToBlank() {
		assertEquals(Constants.GREEN, this.green.getColorStr());
		this.green.setToBlank();
		assertEquals(Constants.BLACK, this.green.getColorStr());
	}

	@Test
	public void testToString() {
	    assertEquals(Constants.GREEN + "  " + Constants.RESET, this.green.toString());
	    assertEquals(Constants.RED + "Hello" + Constants.RESET, this.red.toString("Hello"));
	}
}
