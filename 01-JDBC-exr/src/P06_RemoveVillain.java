import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P06_RemoveVillain {
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

        System.out.println("Enter villain id:");
        int villainId = Integer.parseInt(reader.readLine());

        int affectedEntities = deleteMinionByVillainId(villainId);

        String villainName = findEntityNameById(villainId, "villains");
        deleteVillainById(villainId);

        if(villainId == 0){
            System.out.println("No such villain was found");
            return;
        }

        System.out.printf("%s was deleted%n%d minions released",villainName,affectedEntities);
    }

    private static void deleteVillainById(int villainId) throws SQLException {
        preparedStatement = connection.prepareStatement("DELETE FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);

        preparedStatement.executeUpdate();
    }

    private static int deleteMinionByVillainId(int villainId) throws SQLException {
        preparedStatement = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
        preparedStatement.setInt(1, villainId);

        return preparedStatement.executeUpdate();
    }

    private static String findEntityNameById(int id, String villains) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT name FROM " + villains + " WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next()
                ? resultSet.getString("name")
                : null;
    }
}
