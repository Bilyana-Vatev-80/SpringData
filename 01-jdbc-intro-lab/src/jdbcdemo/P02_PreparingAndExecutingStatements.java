package jdbcdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P02_PreparingAndExecutingStatements {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "soft_uni";
    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,properties);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Double salaryIndicator = Double.parseDouble(reader.readLine());
        reader.close();
        PreparedStatement sqlQuery = connection.prepareStatement(
                "SELECT first_name, last_name \n" +
                        "FROM employees \n" +
                        "WHERE salary > ?"
        );
        sqlQuery.setDouble(1, salaryIndicator);
        ResultSet resultSet = sqlQuery.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
        }

        connection.close();
    }
}
