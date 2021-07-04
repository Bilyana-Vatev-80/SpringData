import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "minions_db";
    public static void main(String[] args) throws SQLException {
        String user = "root";
        String password = "12345";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,props);

    }
}
