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
        System.out.println("What is the contents of your note?");
        String content = scanner.next();

        variables.put(1, String.valueOf(id));
        variables.put(2, title);
        variables.put(3, content);
        database.update(variables, query);

        variables.clear();

    }

    public void removeNote() {

    }

    public HashMap<Integer, String> listAllNotes() throws SQLException {
        int index = 0;
        HashMap<Integer, String> dboutput = new HashMap<>();

        this.query = "SELECT note_title FROM notes WHERE id = ?;";
        variables.put(1, String.valueOf(id));
        ResultSet result = database.fetch(variables, query);

        if (!result.isBeforeFirst()){
            System.out.println("You don't have any saved notes.");
            return null;
        }
        while (result.next()){
            index++;
            System.out.println(index+ ". " + result.getString("note_title"));
            dboutput.put(index, result.getString("note_title"));
        }
        variables.clear();
        return dboutput;
    }


    public void option() throws SQLException {
        System.out.println("""
                        Please select an action below:
                        1. Create a new note
                        2. Delete a note
                        3. Print out a note
                        4. List all notes""");

        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();

        switch (option) {
            case "1" -> {
                addNote();
            }
            case "4" -> {
                listAllNotes();
            }

        }
//        if (option.equals("1")){
//            addNote();
//        } else if (option.equals("4")) {
//            listAllNotes();
//        }
    }
}
