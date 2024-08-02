import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBconnection {

	private static final String URL = "jdbc:mysql://localhost:3306/atm_database";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
