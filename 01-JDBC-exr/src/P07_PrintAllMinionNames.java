import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class P07_PrintAllMinionNames {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "minions_db";
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    public static void main(String[] args) throws SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String user = "root";
        String password = "12345";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,props);

        System.out.println("Entry minions name:");
        preparedStatement = connection.prepareStatement("SELECT name FROM minions;");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> allMinionsName = new LinkedList<>();

        while (resultSet.next()){
            allMinionsName.add(resultSet.getString(1));
        }

        int startIndex = 0;
        int lastIndex = allMinionsName.size() - 1;

        for (int i = 0; i < allMinionsName.size(); i++) {
            System.out.println(i % 2 == 0 ? allMinionsName.get(startIndex++)
                    : allMinionsName.get(lastIndex--));
        }
    }
}
