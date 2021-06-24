package jdbcdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P03_IteratingOverTheResult {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "diablo";
    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,properties);

        PreparedStatement sqlQuery = connection.prepareStatement(
                "SELECT\n" +
                        "  u.first_name,\n" +
                        "  u.last_name,\n" +
                        "  COUNT(ug.id) AS 'games_played'\n" +
                        "FROM users_games ug\n" +
                        "INNER JOIN users u\n" +
                        "  ON ug.user_id = u.id\n" +
                        "WHERE u.last_name = ?\n" +
                        "GROUP BY u.id\n" +
                        "ORDER BY u.id ASC"
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String playerName = reader.readLine();
        reader.close();

        sqlQuery.setString(1, playerName);
        ResultSet resultSet = sqlQuery.executeQuery();

        boolean atLeastOneResult = false;
        while (resultSet.next()) {
            atLeastOneResult = true;
            System.out.println("User: " + playerName);
            System.out.printf("%s %s has played %d games",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("games_played")
            );
        }

        if (!atLeastOneResult)
            System.out.println("No such user exists");

        connection.close();
    }
}
