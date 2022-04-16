import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Database {
    Connection dbconnection;


    public static void loadDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database() {
        Database.loadDriver();

        try {
            dbconnection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/shop?allowPublicKeyRetrieval=true&useSSL=false" +
                        "&allowMultiQueries=true", "skolebruker", "passord");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        try {
            Statement query = dbconnection.createStatement();
            String createUserDB = "CREATE TABLE IF NOT EXISTS users(" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "username VARCHAR(50)," +
                    "pass VARCHAR(50)," +
                    "gid TINYINT)";

            String notesDB = "CREATE TABLE IF NOT EXISTS notes(" +
                    "id INT NOT NULL," +
                    "note_title VARCHAR(100)," +
                    "note_content MEDIUMTEXT," +
                    "FOREIGN KEY (id) REFERENCES users(id))";

            query.execute(createUserDB);
            query.execute(notesDB);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet fetch(HashMap<Integer, String> params, String query) throws SQLException{

        PreparedStatement statement = dbconnection.prepareStatement(query);

        for (Map.Entry<Integer, String> set : params.entrySet()) {
            statement.setString(set.getKey(), set.getValue());
        }

        return statement.executeQuery();
    }

    void update(HashMap<Integer, String> params, String query) throws SQLException{

        PreparedStatement statement = dbconnection.prepareStatement(query);

        for (Map.Entry<Integer, String> set : params.entrySet()) {
            statement.setString(set.getKey(), set.getValue());
        }

        statement.executeUpdate();
    }
}
