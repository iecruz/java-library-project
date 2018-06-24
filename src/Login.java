import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Login {
    public TextField usernameField;
    public TextField passwordField;

    public void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = Database.getUserByUsername(username);

        if(!password.equals(user.password) || user == null) {
            JOptionPane.showMessageDialog(null, "Invalid Username and Password");
            return;
        }

        try {
            FXMLLoader view;

            if(user.id == 1) {
                view = new FXMLLoader(getClass().getResource("main_menu.fxml"));
            } else {
                view = new FXMLLoader(getClass().getResource("book_borrow.fxml"));
            }

            Main.setRoot(view.load());
            Main.user = user;

            Database.execute(String.format("insert into logs(id, user_id) values (%d, %d)",
                    Database.count("logs") + 1,
                    user.id
            ));

            if(user.id == 1) {
                ((MainMenuController) view.getController()).initialize();
            } else {
                ((BookBorrow) view.getController()).initialize();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onExit() {
        Main.exit();
    }
}
