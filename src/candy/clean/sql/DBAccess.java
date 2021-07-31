package candy.clean.sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Todo: hacer que escuche a eventos
public class DBAccess {
	private static final Logger logger = LogManager.getLogger(DBAccess.class);

	private Connection connection;
	private Statement st;
	private ResultSet rs;

	public DBAccess() {
		this.openConnection();
	}

	public void openConnection() {
		String username = "candyclean";
		String password = "Candy_Clean1";
		String dbName = "candyclean";
		String hostname = "localhost";
		String port = "3306";
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?serverTimezone=UTC";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, username, password);

			logger.debug("Connection with the database done");
		} catch (Exception e) {
			logger.debug("Error in the connection. Detailed message:\n");
			logger.debug(e.getMessage());
		}
	}

	public void closeConnection() {
		try {
			if (!this.connection.isClosed()) {
				this.connection.close();
				logger.debug("Connection closed");
			} else {
				logger.debug("Connection already closed");
			}

		} catch (SQLException e) {
			logger.debug("Error while closing the connection with the database");
		}
	}

	public void registerUser(String username) {
		try {
			this.st = this.connection.createStatement();
			this.st.executeUpdate(/*language=SQL*/ "INSERT INTO users (name) VALUES ('" + username + "')");

			logger.debug("User registered ({})", username);
		} catch (SQLException e) {
			logger.warn("Error while inserting the user");
		}
	}

	public void getTop3Users() {
		try {
			this.st = this.connection.createStatement();
			this.rs = this.st.executeQuery(/*language=SQL*/ "SELECT name FROM users LIMIT 3");

			while (this.rs.next()) {
				logger.trace("Name: {}", this.rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.warn("Error while getting the top 3 players");
		}
	}
}
