import java.sql.SQLException;

public class Terminal {

    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        Menu menu = new Menu();

        database.createTables();
        menu.welcome();
    }
}
