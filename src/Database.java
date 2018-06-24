import java.sql.*;
import javafx.collections.*;

public class Database {
    final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
    final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void connect() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, "system", "1234");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void close() {
        try {
            con.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void execute(String query) {
        try {
            connect();
            stmt.executeUpdate(query);
            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }

    public static int count(String table) {
        int count = 0;
        try {
            connect();
            rs = stmt.executeQuery(String.format("select * from %s", table));
            rs.last();
            count = rs.getInt("id");
            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }

        return count;
    }

    public static Book getBook(int id) {
        Book book = new Book();
        try {
            connect();

            String query = String.format("select * from books where id = %d", id);
            rs = stmt.executeQuery(query);
            rs.first();

            book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("description"),
                    rs.getString("status")
            );

            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return book;
    }

    public static ObservableList<Book> getBookMany() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            connect();

            String query = "select * from books";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getString("status")
                );

                books.add(book);
            }
            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return books;
    }

    public static ObservableList<Book> getBookOnShelf() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            connect();

            String query = "select * from books where status = 'On shelf'";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getString("status")
                );

                books.add(book);
            }
            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return books;
    }

    public static User getUser(int id) {
        User user = new User();
        try {
            connect();

            String query = String.format("select * from users where id = %d", id);
            rs = stmt.executeQuery(query);
            rs.first();

            user = new User(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password")
            );

            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return user;
    }

    public static User getUserByUsername(String username) {
        User user = new User();
        try {
            connect();

            String query = String.format("select * from users where username = '%s'", username);
            rs = stmt.executeQuery(query);
            rs.first();

            user = new User(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password")
            );

            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return user;
    }

    public static ObservableList<User> getUserMany() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            connect();

            String query = "select * from users";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password")
                );

                users.add(user);
            }
            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return users;
    }

    public static ObservableList<Transaction> getTransactionMany() {
        ObservableList<Transaction> transactions= FXCollections.observableArrayList();
        try {
            connect();

            String query = "select t1.id, t3.title, t2.first_name, t2.last_name, t1.description from transactions t1, users t2, books t3 where t1.user_id = t2.id and t1.book_id = t3.id";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getString("description")
                );

                transactions.add(transaction);
            }
            close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return transactions;
    }
}