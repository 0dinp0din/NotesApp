import java.sql.*;


public class UserManagement {
    String query;
    Database database = new Database();

    void registerUser(String username, String pass, int gid) throws SQLException {

        if (!userExists(username)) {
            this.query = "INSERT INTO users(username, pass, gid) VALUES (?, ?, ?)";
            PreparedStatement newUser = database.dbconnection.prepareStatement(this.query);
            newUser.setString(1, username);
            newUser.setString(2, pass);
            newUser.setInt(3, gid);

            newUser.execute();


            System.out.printf("User: %s successfully added!", username);
        } else if (userExists(username)) {
            System.out.printf("The username %s already exists.", username);
        }
    }

    /**
     * Checks if user exists
     * @return boolean
     */
    public boolean userExists(String username) throws SQLException {
        this.query = "SELECT * FROM users WHERE username = ?;";
        PreparedStatement findUser = database.dbconnection.prepareStatement(this.query);
        findUser.setString(1, username);

        ResultSet rs = findUser.executeQuery();

        return rs.next();
    }

    public void login(String username, String password) throws SQLException {
        this.query = "SELECT * FROM users WHERE username = ? AND pass = ?;";
        PreparedStatement loginUser = database.dbconnection.prepareStatement(this.query);
        loginUser.setString(1, username);
        loginUser.setString(2, password);

        ResultSet rs = loginUser.executeQuery();
    }
}
