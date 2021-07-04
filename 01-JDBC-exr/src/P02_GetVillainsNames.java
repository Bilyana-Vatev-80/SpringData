import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P02_GetVillainsNames {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "minions_db";
    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String user = "root";
        String password = "12345";

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "12345");

        Connection connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,props);

        System.out.println("Entry villain name:");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT v.name, " +
                "COUNT(DISTINCT mv.minion_id) AS minion_count\n" +
                "FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY v.id\n" +
                "HAVING minion_count > 15\n" +
                "ORDER BY minion_count DESC;");


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %d%n", resultSet.getString("name"),
                    resultSet.getInt("minion_count"));
        }

    }
}
