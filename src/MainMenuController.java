import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainMenuController {

    public BorderPane borderPane;
    public Button accountButton;

    User user;

    public void initialize() {
        this.user = Main.user;

//        if(Main.user.id != 1) {
//            accountButton.setVisible(false);
//        }

        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("book_manager.fxml"));

            borderPane.setCenter(view.load());

            ((BookManager) view.getController()).initialize();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onBookManager() {
        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("book_manager.fxml"));

            borderPane.setCenter(view.load());

            ((BookManager) view.getController()).initialize();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onUserManager() {
        try {
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("user_manager.fxml")));
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onTransactions() {
        try {
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("transactions.fxml")));
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onLogout() {
        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("login.fxml"));

            Main.setRoot(view.load());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onExit() {
        Main.exit();
    }
}
