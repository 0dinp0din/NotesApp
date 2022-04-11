import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Menu {

    Scanner input = new Scanner(System.in);
    UserManagement usermgmt = new UserManagement();

    String[] text = {
            "Welcome",
            "Please select an option:",
            "1. Create account\n2. Login"
    };

    void welcome() throws SQLException {
        System.out.println("Welcome. Please select an option:\n1. Create account\n2. Login");
        String option = input.nextLine();

        while (!Objects.equals(option, "1") && !Objects.equals(option, "2")) {
            System.out.println("That was not one of the options.. Try again");
            option = input.nextLine();
        }

        if (Objects.equals(option, "1")){
            registration();
        } else if (Objects.equals(option, "2")){
            System.out.println("todo");

        }
    }

    void registration() throws SQLException {
        System.out.println("To create an account, type your details below:");
        System.out.print("Username: ");
        String username = input.next();

        System.out.print("Password: ");
        String password = input.next();

        usermgmt.registerUser(username, password, 100);
    }
}
