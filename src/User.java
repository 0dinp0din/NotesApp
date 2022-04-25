import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;

public class User {
    Database database = new Database();

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public int getGid() {
        return gid;
    }

    int id;
    String username;
    int gid;

    public User(int id, String username, int gid) {
        this.id = id;
        this.username = username;
        this.gid = gid;
    }

    public void addNote() throws SQLException{

        String query = "INSERT INTO notes(id, note_title, note_content) VALUES (?, ?, ?)";
        Scanner scanner = new Scanner(System.in);

        System.out.println("What should the title of your note be?");
        String title = scanner.next();

        if (!titleExists(title)) {
            HashMap<Integer, String> variables = new HashMap<>();
            System.out.println("What is the contents of your note?");
            String content = scanner.next();

            variables.put(1, String.valueOf(id));
            variables.put(2, title);
            variables.put(3, content);
            database.update(variables, query);

        } else if (titleExists(title)) {
            System.out.printf("A note with the title \"%s\" already exists.", title);
        } else {
            System.out.println("An unknown error has occurred..");
        }

    }

    public void removeNote() throws SQLException {
        HashMap<Integer, String> variables = new HashMap<>();
        String query = "DELETE FROM notes WHERE id = ? AND note_title = ?;";
        Scanner input = new Scanner(System.in);
        HashMap<String, String> list = listAllNotes();
        System.out.println("Type the number of the note you would like to delete:");
        String selection = input.next();

        variables.put(1, String.valueOf(this.id));
        variables.put(2, list.get(selection));

        System.out.printf("Are you sure you want to delete \"%s\" ? (y/n)", list.get(selection));
        String yn = input.next();
        while (!yn.equalsIgnoreCase("y") && !yn.equalsIgnoreCase("n")) {
            System.out.println("That was not an option.. Try again");
            yn = input.next();
        }

        if (yn.equalsIgnoreCase("y")) {
            database.update(variables, query);
            System.out.printf("Note titled: %s was successfully removed..", list.get(selection));
        }
    }

    public boolean titleExists(String title) throws SQLException {
        HashMap<Integer, String> existsCache = new HashMap<>();

        String query = "SELECT id, note_title FROM notes WHERE id = ? AND note_title = ?;";
        existsCache.put(1, String.valueOf(this.id));
        existsCache.put(2, title);
        ResultSet rs = database.fetch(existsCache, query);
        return rs.next();
    }

    public HashMap<String, String> listAllNotes() throws SQLException {
        HashMap<Integer, String> variables = new HashMap<>();
        int index = 0;
        HashMap<String, String> dboutput = new HashMap<>();

        String query = "SELECT note_title FROM notes WHERE id = ?;";
        variables.put(1, String.valueOf(id));
        ResultSet result = database.fetch(variables, query);

        if (!result.isBeforeFirst()){
            System.out.println("You don't have any saved notes.\n");
            return null;
        }else {
            System.out.println("Your saved notes are:");
        }
        while (result.next()){
            index++;
            System.out.println(index+ ". " + result.getString("note_title"));
            dboutput.put(String.valueOf(index), result.getString("note_title"));
        }
        return dboutput;
    }

    public void printNote() throws SQLException {
        HashMap<Integer, String> variables = new HashMap<>();
        HashMap<String, String> list = listAllNotes();

        if(list == null) {
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Type the number of the note you would like to print:");
        String selection = input.next();

        String query = "SELECT note_content FROM notes WHERE id = ? AND note_title = ?;";
        variables.put(1, String.valueOf(this.id));
        variables.put(2, list.get(selection));
        ResultSet result = database.fetch(variables, query);

        while (result.next()){
            System.out.println(result.getString("note_content"));
        }
    }


    public void option() throws SQLException {
        boolean exit = false;

        while (!exit){
            System.out.println("""
                        Please select an action below:
                        1. Create a new note
                        2. Delete a note
                        3. Print out a note
                        4. Exit application""");

            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1" -> {
                    addNote();
                }case "2" -> {
                    removeNote();
                }case "3" -> {
                    printNote();
                }case "4" -> {
                    System.out.println("Goodbye!");
                    exit = true;
                }
            }
        }
    }
}
