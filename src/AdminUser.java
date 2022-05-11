import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminUser extends User {
    public AdminUser(int id, String username, int gid) {
        super(id, username, gid);
    }

    public void listUsers() throws SQLException {
        String query = "SELECT id, username, gid FROM users;";

        Statement statement = database.dbconnection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()){
            System.out.printf("%s. %s   %s\n", rs.getString("id"), rs.getString("username"), rs.getString("gid"));
        }
    }

    public void removeUser() {}

    @Override
    public void option() throws SQLException {
        boolean exit = false;

        while(!exit) {
            System.out.println("""
                        Please select an action below:
                        1. Create a new note
                        2. Delete a note
                        3. Print out a note
                        4. (Admin only) List all users
                        5. (Admin only) Show user's notes
                        6. Exit application""");

            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> {
                    addNote();
                }case "2" -> {
                    removeNote();
                }case "3" -> {
                    printNote();
                }case "4" -> {
                    listUsers();
                }case "5" -> {
                    removeUser();
                }case "6" -> {
                    System.out.println("Goodbye!");
                    exit = true;
                }
            }
        }
    }
}
