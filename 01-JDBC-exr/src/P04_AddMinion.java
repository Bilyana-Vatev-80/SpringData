import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P04_AddMinion {
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

        System.out.println("Enter minion parameters:");
        String[] minionsInfo = reader.readLine().split("\\s+");
        String minionName = minionsInfo[0];
        int age = Integer.parseInt(minionsInfo[1]);
        String townName = minionsInfo[2];
        int townId = getEntityNameByName(townName, "towns");
        int minionId = getEntityNameByName(minionName, "minions");

        if(minionId < 0){
            if(townId < 0){
                insertEntityInTowns(townName);
                System.out.printf("Town %s was added to the database.%n",townName);
                townId = getEntityNameByName(townName, "towns");
            }
            insertEntityInMinions(minionName,age,townId);
            minionId = getEntityNameByName(minionName, "minions");
        }
        System.out.println("Entry villain name:");
        String villainName = reader.readLine();
        int villainId = getEntityNameByName(villainName, "villains");
        if(villainId < 0){
            insertEntityInVillains(villainName);
            System.out.printf("Villain %s was added to the database.%n",villainName);
            villainId = getEntityNameByName(villainName, "villains");
        }

        insertEntityInMinionsVillains(minionId,villainId);
        System.out.printf("Successfully added %s to be minion of %s.",minionName,villainName);
    }

    private static void insertEntityInMinionsVillains(int minionId, int villainId) throws SQLException {
        String query = "INSERT INTO minions_villains(minion_id,villain_id) value (?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.execute();
    }

    private static void insertEntityInVillains(String villainName) throws SQLException {
        String query = "INSERT INTO villains(name,evilness_factor) value(?, 'evil')";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, villainName);
        preparedStatement.execute();
    }

    private static void insertEntityInMinions(String minionName, int age, int town_id) throws SQLException {
        String query = "INSERT INTO minions(name,age,town_id) value(?,?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,minionName);
        preparedStatement.setInt(2, age);
        preparedStatement.setInt(3, town_id);
        preparedStatement.execute();
    }

    private static void insertEntityInTowns(String townName) throws SQLException {
        String query = "INSERT INTO towns(name) value(?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, townName);
        preparedStatement.execute();
    }

    private static int getEntityNameByName(String entityName, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entityName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? resultSet.getInt(1) : -1;
    }
}
