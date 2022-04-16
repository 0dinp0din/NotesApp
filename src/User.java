import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;

public class User {
    Database database = new Database();
    String query;
    HashMap<Integer, String> variables = new HashMap<>();

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

        this.query = "INSERT INTO notes(id, note_title, note_content) VALUES (?, ?, ?)";
        Scanner scanner = new Scanner(System.in);

        System.out.println("What should the title of your note be?");
        String title = scanner.next();

        if (!titleExists(title)) {
            System.out.println("What is the contents of your note?");
            String content = scanner.next();

            variables.put(1, String.valueOf(id));
            variables.put(2, title);
            variables.put(3, content);
            database.update(variables, query);

            variables.clear();
        } else if (titleExists(title)) {
            System.out.printf("A note with the title \"%s\" already exists.", title);
        } else {
            System.out.println("An unknown error has occurred..");
        }

    }

    public void removeNote() {

    }

    public boolean titleExists(String title) throws SQLException {
        this.query = "SELECT note_title FROM notes WHERE note_title = ?;";
        variables.put(1, title);
        ResultSet rs = database.fetch(variables, query);
        return rs.next();
    }

    public HashMap<String, String> listAllNotes() throws SQLException {
        int index = 0;
        HashMap<String, String> dboutput = new HashMap<>();

        this.query = "SELECT note_title FROM notes WHERE id = ?;";
        variables.put(1, String.valueOf(id));
        ResultSet result = database.fetch(variables, query);

        if (!result.isBeforeFirst()){
            System.out.println("You don't have any saved notes.");
            return null;
        }else {
            System.out.println("Your saved notes are:");
        }
        while (result.next()){
            index++;
            System.out.println(index+ ". " + result.getString("note_title"));
            dboutput.put(String.valueOf(index), result.getString("note_title"));
        }
        variables.clear();
        return dboutput;
    }

    public void printNote() throws SQLException {
        Scanner input = new Scanner(System.in);
        HashMap<String, String> list = listAllNotes();
        System.out.println("Type the number of the note you would like to print:");
        String selection = input.next();

        query = "SELECT note_content FROM notes WHERE id = ? AND note_title = ?;";
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
