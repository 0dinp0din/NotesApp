import java.sql.SQLException;

public class Terminal {

    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        UserManagement registration = new UserManagement();
        Menu menu = new Menu();
        database.createTables();

        menu.welcome();

        //registration.registerUser("odin", "123", 0);
    }
}
