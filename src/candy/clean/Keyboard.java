package candy.clean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Keyboard {

	private Keyboard() {
		throw new UnsupportedOperationException("Utility class");
	}

	public static String read() throws CandyCleanException {
		String output;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			output = reader.readLine();
		} catch (IOException e) {
			throw new CandyCleanException("An error occurred with I/O");
		}

		return output;
	}
}
