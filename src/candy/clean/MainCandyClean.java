package candy.clean;

import candy.clean.sql.DBAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainCandyClean {
	private static final Logger logger = LogManager.getLogger(MainCandyClean.class);

	public static void main(String[] args) {
		DBAccess dbAccess = new DBAccess();
		Runtime.getRuntime().addShutdownHook(new Thread(dbAccess::closeConnection));

		UI ui = new UI();
		ui.setDbAccess(dbAccess);
		ui.init();
	}
}
