import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P03_GetMinionNames {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "minions_db";
    private static PreparedStatement preparedStatement;
    private static Connection connection;
    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String user = "root";
        String password = "12345";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,props);

        System.out.println("Enter villain id:");

        int id = Integer.parseInt(reader.readLine());

        preparedStatement = connection.prepareStatement("SELECT name FROM villains\n" +
                "WHERE id = ?;");

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(!resultSet.next()){
            System.out.printf("No villain with ID %d exists in the database.", id);
            return;
        }
        System.out.printf("Villain: %s%n", getEntityNameById(id, "villains"));

        getMinionsAndAgeVillainId(id);
    }

    private static void getMinionsAndAgeVillainId(int id) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT m.name , m.age FROM minions AS m\n" +
                "JOIN minions_villains AS mv\n" +
                "ON m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?");

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0;

        while (resultSet.next()){
            System.out.printf("%d. %s %d%n", ++count,
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
        }
    }

    private static String getEntityNameById(int id, String villains) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT name FROM " + villains + " WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next()
                ? resultSet.getString("name")
                : null;
    }
}
