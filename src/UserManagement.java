import java.sql.*;
import java.util.HashMap;


public class UserManagement {
    String query;
    Database database = new Database();

    void registerUser(String username, String pass, int gid) throws SQLException {

        if (!userExists(username)) {
            this.query = "INSERT INTO users(username, pass, gid) VALUES (?, ?, ?)";


            HashMap<Integer, String> userDetails = new HashMap<>();
            userDetails.put(1, username);
            userDetails.put(2, pass);
            userDetails.put(3, String.valueOf(gid));

            database.update(userDetails, query);


            System.out.printf("User: %s successfully added!", username);
        } else if (userExists(username)) {
            System.out.printf("The username %s already exists.", username);
        }
    }


    public boolean userExists(String username) throws SQLException {
        this.query = "SELECT * FROM users WHERE username = ?;";
        PreparedStatement findUser = database.dbconnection.prepareStatement(this.query);
        findUser.setString(1, username);

        ResultSet rs = findUser.executeQuery();

        return rs.next();
    }


    public User login(String username, String password) throws SQLException {
        this.query = "SELECT id, username, gid FROM users WHERE username = ? AND pass = ?;";
        PreparedStatement loginUser = database.dbconnection.prepareStatement(this.query);
        loginUser.setString(1, username);
        loginUser.setString(2, password);

        ResultSet rs = loginUser.executeQuery();
        boolean auth = rs.next();

        if (!auth) {
            System.out.println("Wrong username or password.. Try again");
            return null;
        } else {
            System.out.printf("Welcome back %s! ", username);

            switch (rs.getInt("gid")) {
                case 0 -> {
                    return new AdminUser(rs.getInt("id"), rs.getString("username"), rs.getInt("gid"));
                }
                case 100 -> {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getInt("gid"));
                }
            }
        }
        System.out.println("An unknown error has occurred. Please restart the application...");
        return null;
    }
}
