import java.sql.*;

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
                    "id NOT NULL," +
                    "note_title VARCHAR(100)," +
                    "note_content";

            query.execute(createUserDB);



        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
