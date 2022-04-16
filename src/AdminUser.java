import java.sql.SQLException;
import java.util.Scanner;

public class AdminUser extends User {
    public AdminUser(int id, String username, int gid) {
        super(id, username, gid);
    }
    public void listUsers() {}

    public void removeUser() {}

    @Override
    public void option() throws SQLException {
        System.out.println("""
                        Please select an action below:
                        1. Create a new note
                        2. Delete a note
                        3. Print out a note
                        4. List all notes
                        5. (Admin only) List all users
                        6. (Admin only) Show user's notes""");

        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();

        if (option.equals("1")){
            System.out.println("Test");
        } else if (option.equals("4")) {
            listAllNotes();
        }
    }
}
