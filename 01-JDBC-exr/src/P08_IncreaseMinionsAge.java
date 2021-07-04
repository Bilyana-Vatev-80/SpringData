import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P08_IncreaseMinionsAge {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "minions_db";
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String user = "root";
        String password = "12345";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,props);

        System.out.println("Enter minion id:");
        String[] tokens = reader.readLine().split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
            int currentId = Integer.parseInt(tokens[i]);
            incrementAge(currentId);
        }
        printMinions();
    }

    private static void printMinions() throws SQLException {
        String query = "SELECT name, age FROM minions";
        preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %d%n", resultSet.getString(1),resultSet.getInt(2));
        }
    }

    private static void incrementAge(int currentId) throws SQLException {
        String query = "UPDATE minions SET age = age + 1, name = lower(name) WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, currentId);
        preparedStatement.executeUpdate();

    }
}
