import javafx.fxml.FXMLLoader;

import javax.swing.*;

public class UserAdd {
    public javafx.scene.control.TextField firstNameField;
    public javafx.scene.control.TextField lastNameField;
    public javafx.scene.control.TextField usernameField;
    public javafx.scene.control.PasswordField passwordField;
    public javafx.scene.control.PasswordField confirmPasswordField;

    public void onAdd() {
        if(!passwordField.getText().equals(confirmPasswordField.getText())) {
            JOptionPane.showMessageDialog(null, "Password did not match");
            return;
        }

        Database.execute(String.format("insert into users(id, first_name, last_name, username, password) values(%d, '%s', '%s', '%s', '%s')",
                Database.count("users") + 1,
                firstNameField.getText(),
                lastNameField.getText(),
                usernameField.getText(),
                passwordField.getText()
        ));

        onCancel();
    }

    public void onCancel() {
        try {
            FXMLLoader bookAdd = new FXMLLoader(getClass().getResource("main_menu.fxml"));

            Main.setRoot(bookAdd.load());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
