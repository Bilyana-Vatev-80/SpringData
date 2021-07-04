import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P05_ChangeTownNamesCasing {
    private static  final  String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static  final  String DB_NAME = "minions_db";
    private static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String user = "root";
        String password = "12345";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        connection = DriverManager.
                getConnection(CONNECTION_STRING + DB_NAME,props);

        System.out.println("Entry country name:");
        String countryName = reader.readLine();

       PreparedStatement preparedStatement = connection.prepareStatement("UPDATE towns SET name = UPPER(name)" +
                "WHERE country = ?;");

        preparedStatement.setString(1,countryName);

        int effectedRows = preparedStatement.executeUpdate();

        if(effectedRows == 0){
            System.out.println("No town names were affected.");
            return;
        }

        System.out.printf("%d town names effected.%n",effectedRows);

        PreparedStatement preparedStatementTowns = connection.prepareStatement("SELECT name FROM towns WHERE country = ?");
        preparedStatementTowns.setString(1, countryName);
        ResultSet resultSet = preparedStatementTowns.executeQuery();

        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }

    }
}
